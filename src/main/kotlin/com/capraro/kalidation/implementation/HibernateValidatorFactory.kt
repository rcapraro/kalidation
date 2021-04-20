/*
 * The MIT License
 *
 * Copyright (c) 2018 Richard Capraro
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.capraro.kalidation.implementation

import com.capraro.kalidation.constraints.rule.ConstraintRule
import com.capraro.kalidation.constraints.rule.Valid
import com.capraro.kalidation.spec.PropertyConstraint
import com.capraro.kalidation.spec.ValidationSpec
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.hibernate.validator.HibernateValidator
import org.hibernate.validator.cfg.ConstraintDef
import org.hibernate.validator.cfg.ConstraintMapping
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping
import org.hibernate.validator.internal.engine.DefaultPropertyNodeNameProvider
import org.hibernate.validator.internal.properties.DefaultGetterPropertySelectionStrategy
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator
import java.util.Locale

/**
 * Transforms a [ValidationSpec] into an Hibernate validator spec.
 * @see ValidationSpec
 * @author Richard Capraro
 * @since 0.0.1
 */
class HibernateValidatorFactory(private val spec: ValidationSpec) {

    class ConstraintRuleTranslator<C : ConstraintRule>(private val block: (C) -> ConstraintDef<*, *>) {
        fun translate(c: C): ConstraintDef<*, *> = block.invoke(c)
    }

    private fun createConstraintMapping(spec: ValidationSpec): ConstraintMapping {
        return with(spec) {
            val constraintMapping = DefaultConstraintMapping(
                JavaBeanHelper(
                    DefaultGetterPropertySelectionStrategy(),
                    DefaultPropertyNodeNameProvider()
                )
            )
            constraints.forEach { constraint ->
                val typeMapping = constraintMapping.type(constraint.constrainedClass.java)
                constraint.propertyConstraints.forEach { propertyConstraint ->
                    val propertyMapping = typeMapping.field(propertyConstraint.constrainedProperty.name)

                    createContainerConstraintMapping(propertyConstraint, propertyMapping)

                    propertyConstraint.constraintRules.forEach { rule: ConstraintRule ->
                        val context = propertyMapping.constraint(translateConstraintDef(rule))
                        if (rule is Valid) context.valid()
                    }
                }

                constraint.methodConstraints.forEach { methodConstraint ->
                    val methodReturnValue = typeMapping.method(methodConstraint.constrainedMethod.name).returnValue()
                    methodConstraint.constraintRules.forEach { rule: ConstraintRule ->
                        methodReturnValue.constraint(translateConstraintDef(rule))
                    }
                }
            }
            constraintMapping
        }
    }

    private fun createContainerConstraintMapping(
        propertyConstraint: PropertyConstraint<out Any, out Any?>,
        propertyMapping: PropertyConstraintMappingContext
    ) {
        if (propertyConstraint.containerElementsTypes.isNotEmpty()) {
            propertyConstraint.containerElementsTypes.forEach { containerElementType ->
                val hvContainerElementType = propertyMapping.containerElementType(
                    containerElementType.indexes.head,
                    *containerElementType.indexes.tail.toIntArray()
                )
                containerElementType.constraintRules.forEach { rule: ConstraintRule ->
                    val context = hvContainerElementType.constraint(rule.translate())
                    if (rule is Valid) context.valid()
                }
            }
        }
    }

    private fun translateConstraintDef(rule: ConstraintRule): ConstraintDef<*, *> = rule.translate()

    internal fun build(locale: Locale, messageBundle: String?): Validator {

        val validatorConfiguration = Validation
            .byProvider(HibernateValidator::class.java)
            .configure()

        return validatorConfiguration
            .messageInterpolator(
                LocaleSpecificMessageInterpolator(
                    locale,
                    AggregateResourceBundleLocator(listOfNotNull(messageBundle, "ValidationMessages"))
                )
            )
            .addMapping(createConstraintMapping(spec))
            .buildValidatorFactory().validator
    }
}
