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

import com.capraro.kalidation.constraints.def.*
import com.capraro.kalidation.constraints.rule.*
import com.capraro.kalidation.spec.ValidationSpec
import org.hibernate.validator.HibernateValidator
import org.hibernate.validator.cfg.ConstraintDef
import org.hibernate.validator.cfg.ConstraintMapping
import org.hibernate.validator.cfg.defs.*
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator
import java.lang.annotation.ElementType
import java.util.*
import javax.validation.Validation
import javax.validation.Validator

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
            val constraintMapping = DefaultConstraintMapping()
            constraints.forEach { constraint ->
                val typeMapping = constraintMapping.type(constraint.constrainedClass.java)
                constraint.propertyConstraints.forEach { propertyConstraint ->
                    val propertyMapping = typeMapping.property(propertyConstraint.constrainedProperty.name, ElementType.FIELD)
                    propertyConstraint.constraintRules.forEach { rule: ConstraintRule ->
                        val context = propertyMapping.constraint(translateConstraintDef(rule))
                        when (rule) {
                            is Valid -> context.valid()
                        }
                    }
                }

                constraint.containerPropertyConstraints.forEach { propertyConstraint ->
                    val propertyMapping = typeMapping.property(propertyConstraint.constrainedProperty.name, ElementType.FIELD)
                    val containerElementType = propertyMapping.containerElementType(propertyConstraint.indexes.head, *propertyConstraint.indexes.tail.toIntArray())
                    propertyConstraint.constraintRules.forEach { rule: ConstraintRule ->
                        val context = containerElementType.constraint(translateConstraintDef(rule))
                        when (rule) {
                            is Valid -> context.valid()
                        }
                    }
                }
                constraint.methodConstraints.forEach { methodConstraint ->
                    val methodReturnValue = typeMapping.method(methodConstraint.constrainedMethod.name).returnValue()
                    methodConstraint.constraintRules.forEach { rule: ConstraintRule ->
                        methodReturnValue.constraint(translateConstraintDef(rule)).valid()
                    }
                }
            }
            constraintMapping
        }
    }

    private fun ConstraintDef<*, *>.customMessage(message: String?): ConstraintDef<*, *> {
        message?.let {
            this.message(message)
        }
        return this
    }

    private fun translateConstraintDef(rule: ConstraintRule): ConstraintDef<*, *> = when (rule) {

        //hack: Valid is notNull
        is Valid -> ConstraintRuleTranslator<Valid> { NotNullDef().customMessage(it.message) }.translate(rule)
        is ValidByScript -> ConstraintRuleTranslator<ValidByScript> { ScriptAssertDef().lang(it.lang).script(it.script).alias(it.alias).reportOn(it.reportOn).customMessage(it.message) }.translate(rule)

        is NotNull -> ConstraintRuleTranslator<NotNull> { NotNullDef().customMessage(it.message) }.translate(rule)
        is Null -> ConstraintRuleTranslator<Null> { NullDef().customMessage(it.message) }.translate(rule)

        is ArraySize -> ConstraintRuleTranslator<ArraySize> { SizeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)
        is ArrayNotEmpty -> ConstraintRuleTranslator<ArrayNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(rule)

        is ColSize -> ConstraintRuleTranslator<ColSize> { SizeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)
        is ColNotEmpty -> ConstraintRuleTranslator<ColNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(rule)

        is AssertTrue -> ConstraintRuleTranslator<AssertTrue> { AssertTrueDef().customMessage(it.message) }.translate(rule)
        is AssertFalse -> ConstraintRuleTranslator<AssertFalse> { AssertFalseDef().customMessage(it.message) }.translate(rule)

        is NotBlank -> ConstraintRuleTranslator<NotBlank> { NotBlankDef().customMessage(it.message) }.translate(rule)
        is CsNotEmpty -> ConstraintRuleTranslator<CsNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(rule)
        is CsSize -> ConstraintRuleTranslator<CsSize> { SizeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)
        is Regexp -> ConstraintRuleTranslator<Regexp> { PatternDef().regexp(it.regexp).customMessage(it.message) }.translate(rule)
        is Email -> ConstraintRuleTranslator<Email> { EmailDef().customMessage(it.message) }.translate(rule)
        is PhoneNumber -> ConstraintRuleTranslator<PhoneNumber> { PhoneNumberDef().regionCode(it.regionCode).customMessage(it.message) }.translate(rule)
        is InValues -> ConstraintRuleTranslator<InValues> { ValuesDef().values(it.values.toTypedArray()).customMessage(it.message) }.translate(rule)
        is CsMin -> ConstraintRuleTranslator<CsMin> { MinDef().value(it.value).customMessage(it.message) }.translate(rule)
        is CsMax -> ConstraintRuleTranslator<CsMax> { MaxDef().value(it.value).customMessage(it.message) }.translate(rule)
        is CsDecimalMin -> ConstraintRuleTranslator<CsDecimalMin> { DecimalMinDef().value(it.value).inclusive(it.inclusive).customMessage(it.message) }.translate(rule)
        is CsDecimalMax -> ConstraintRuleTranslator<CsDecimalMax> { DecimalMaxDef().value(it.value).inclusive(it.inclusive).customMessage(it.message) }.translate(rule)
        is CsPositive -> ConstraintRuleTranslator<CsPositive> { CsPositiveDef().customMessage(it.message) }.translate(rule)
        is CsPositiveOrZero -> ConstraintRuleTranslator<CsPositiveOrZero> { CsPositiveOrZeroDef().customMessage(it.message) }.translate(rule)
        is CsNegative -> ConstraintRuleTranslator<CsNegative> { CsNegativeDef().customMessage(it.message) }.translate(rule)
        is CsNegativeOrZero -> ConstraintRuleTranslator<CsNegativeOrZero> { CsNegativeOrZeroDef().customMessage(it.message) }.translate(rule)
        is CsRange -> ConstraintRuleTranslator<CsRange> { RangeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)
        is CsDigits -> ConstraintRuleTranslator<CsDigits> { DigitsDef().integer(it.integer).fraction(it.fraction).customMessage(it.message) }.translate(rule)
        is Iso8601Date -> ConstraintRuleTranslator<Iso8601Date> { Iso8601DateDef().customMessage(it.message) }.translate(rule)

        is NegativeOrZero -> ConstraintRuleTranslator<NegativeOrZero> { NegativeOrZeroDef().customMessage(it.message) }.translate(rule)
        is PositiveOrZero -> ConstraintRuleTranslator<PositiveOrZero> { PositiveOrZeroDef().customMessage(it.message) }.translate(rule)
        is Negative -> ConstraintRuleTranslator<Negative> { NegativeDef().customMessage(it.message) }.translate(rule)
        is Positive -> ConstraintRuleTranslator<Positive> { PositiveDef().customMessage(it.message) }.translate(rule)
        is Min -> ConstraintRuleTranslator<Min> { MinDef().value(it.value).customMessage(it.message) }.translate(rule)
        is Max -> ConstraintRuleTranslator<Max> { MaxDef().value(it.value).customMessage(it.message) }.translate(rule)
        is DecimalMin -> ConstraintRuleTranslator<DecimalMin> { DecimalMinDef().value(it.value).inclusive(it.inclusive).customMessage(it.message) }.translate(rule)
        is DecimalMax -> ConstraintRuleTranslator<DecimalMax> { DecimalMaxDef().value(it.value).inclusive(it.inclusive).customMessage(it.message) }.translate(rule)
        is Digits -> ConstraintRuleTranslator<Digits> { DigitsDef().integer(it.integer).fraction(it.fraction).customMessage(it.message) }.translate(rule)
        is Range -> ConstraintRuleTranslator<Range> { RangeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)

        is Future -> ConstraintRuleTranslator<Future> { FutureDef().customMessage(it.message) }.translate(rule)
        is FutureOrPresent -> ConstraintRuleTranslator<FutureOrPresent> { FutureOrPresentDef().customMessage(it.message) }.translate(rule)
        is Past -> ConstraintRuleTranslator<Past> { PastDef().customMessage(it.message) }.translate(rule)
        is PastOrPresent -> ConstraintRuleTranslator<PastOrPresent> { PastOrPresentDef().customMessage(it.message) }.translate(rule)
        is SubSetOf -> ConstraintRuleTranslator<SubSetOf> { SubSetDef().completeValues(it.values.toTypedArray()).customMessage(it.message) }.translate(rule)

        is MapHasKeys -> ConstraintRuleTranslator<MapHasKeys> { MapHasKeysDef().mapKeys(it.keys.toTypedArray()).customMessage(it.message) }.translate(rule)
        is MapNotEmpty -> ConstraintRuleTranslator<MapNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(rule)
        is MapSize -> ConstraintRuleTranslator<MapSize> { SizeDef().min(it.min).max(it.max).customMessage(it.message) }.translate(rule)
    }

    internal fun build(locale: Locale, messageBundle: String?): Validator {

        val validatorConfiguration = Validation
                .byProvider(HibernateValidator::class.java)
                .configure()

        return validatorConfiguration
                .messageInterpolator(LocaleSpecificMessageInterpolator(locale,
                        AggregateResourceBundleLocator(listOfNotNull(messageBundle, "ValidationMessages"))))
                .addMapping(createConstraintMapping(spec))
                .buildValidatorFactory().validator
    }
}