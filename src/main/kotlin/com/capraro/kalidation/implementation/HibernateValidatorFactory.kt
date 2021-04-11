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

import com.capraro.kalidation.constraints.def.CsNegativeDef
import com.capraro.kalidation.constraints.def.CsNegativeOrZeroDef
import com.capraro.kalidation.constraints.def.CsPositiveDef
import com.capraro.kalidation.constraints.def.CsPositiveOrZeroDef
import com.capraro.kalidation.constraints.def.InIso8601DateRangeDef
import com.capraro.kalidation.constraints.def.Iso8601DateDef
import com.capraro.kalidation.constraints.def.MapHasKeysDef
import com.capraro.kalidation.constraints.def.PhoneNumberDef
import com.capraro.kalidation.constraints.def.SubSetDef
import com.capraro.kalidation.constraints.def.ValuesDef
import com.capraro.kalidation.constraints.rule.ArrayNotEmpty
import com.capraro.kalidation.constraints.rule.ArraySize
import com.capraro.kalidation.constraints.rule.AssertFalse
import com.capraro.kalidation.constraints.rule.AssertTrue
import com.capraro.kalidation.constraints.rule.ColNotEmpty
import com.capraro.kalidation.constraints.rule.ColSize
import com.capraro.kalidation.constraints.rule.ConstraintRule
import com.capraro.kalidation.constraints.rule.CsDecimalMax
import com.capraro.kalidation.constraints.rule.CsDecimalMin
import com.capraro.kalidation.constraints.rule.CsDigits
import com.capraro.kalidation.constraints.rule.CsMax
import com.capraro.kalidation.constraints.rule.CsMin
import com.capraro.kalidation.constraints.rule.CsNegative
import com.capraro.kalidation.constraints.rule.CsNegativeOrZero
import com.capraro.kalidation.constraints.rule.CsNotEmpty
import com.capraro.kalidation.constraints.rule.CsPositive
import com.capraro.kalidation.constraints.rule.CsPositiveOrZero
import com.capraro.kalidation.constraints.rule.CsRange
import com.capraro.kalidation.constraints.rule.CsSize
import com.capraro.kalidation.constraints.rule.DecimalMax
import com.capraro.kalidation.constraints.rule.DecimalMin
import com.capraro.kalidation.constraints.rule.Digits
import com.capraro.kalidation.constraints.rule.Email
import com.capraro.kalidation.constraints.rule.Future
import com.capraro.kalidation.constraints.rule.FutureOrPresent
import com.capraro.kalidation.constraints.rule.InIso8601DateRange
import com.capraro.kalidation.constraints.rule.InValues
import com.capraro.kalidation.constraints.rule.Iso8601Date
import com.capraro.kalidation.constraints.rule.MapHasKeys
import com.capraro.kalidation.constraints.rule.MapNotEmpty
import com.capraro.kalidation.constraints.rule.MapSize
import com.capraro.kalidation.constraints.rule.Max
import com.capraro.kalidation.constraints.rule.Min
import com.capraro.kalidation.constraints.rule.Negative
import com.capraro.kalidation.constraints.rule.NegativeOrZero
import com.capraro.kalidation.constraints.rule.NotBlank
import com.capraro.kalidation.constraints.rule.NotNull
import com.capraro.kalidation.constraints.rule.Null
import com.capraro.kalidation.constraints.rule.Past
import com.capraro.kalidation.constraints.rule.PastOrPresent
import com.capraro.kalidation.constraints.rule.PhoneNumber
import com.capraro.kalidation.constraints.rule.Positive
import com.capraro.kalidation.constraints.rule.PositiveOrZero
import com.capraro.kalidation.constraints.rule.Range
import com.capraro.kalidation.constraints.rule.Regexp
import com.capraro.kalidation.constraints.rule.SubSetOf
import com.capraro.kalidation.constraints.rule.Valid
import com.capraro.kalidation.constraints.rule.ValidByScript
import com.capraro.kalidation.spec.PropertyConstraint
import com.capraro.kalidation.spec.ValidationSpec
import org.hibernate.validator.HibernateValidator
import org.hibernate.validator.cfg.ConstraintDef
import org.hibernate.validator.cfg.ConstraintMapping
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext
import org.hibernate.validator.cfg.defs.AssertFalseDef
import org.hibernate.validator.cfg.defs.AssertTrueDef
import org.hibernate.validator.cfg.defs.DecimalMaxDef
import org.hibernate.validator.cfg.defs.DecimalMinDef
import org.hibernate.validator.cfg.defs.DigitsDef
import org.hibernate.validator.cfg.defs.EmailDef
import org.hibernate.validator.cfg.defs.FutureDef
import org.hibernate.validator.cfg.defs.FutureOrPresentDef
import org.hibernate.validator.cfg.defs.MaxDef
import org.hibernate.validator.cfg.defs.MinDef
import org.hibernate.validator.cfg.defs.NegativeDef
import org.hibernate.validator.cfg.defs.NegativeOrZeroDef
import org.hibernate.validator.cfg.defs.NotBlankDef
import org.hibernate.validator.cfg.defs.NotEmptyDef
import org.hibernate.validator.cfg.defs.NotNullDef
import org.hibernate.validator.cfg.defs.NullDef
import org.hibernate.validator.cfg.defs.PastDef
import org.hibernate.validator.cfg.defs.PastOrPresentDef
import org.hibernate.validator.cfg.defs.PatternDef
import org.hibernate.validator.cfg.defs.PositiveDef
import org.hibernate.validator.cfg.defs.PositiveOrZeroDef
import org.hibernate.validator.cfg.defs.RangeDef
import org.hibernate.validator.cfg.defs.ScriptAssertDef
import org.hibernate.validator.cfg.defs.SizeDef
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping
import org.hibernate.validator.internal.engine.DefaultPropertyNodeNameProvider
import org.hibernate.validator.internal.properties.DefaultGetterPropertySelectionStrategy
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator
import java.util.Locale
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
                        when (rule) {
                            is Valid -> context.valid()
                            else -> {
                            }
                        }
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
                    val context = hvContainerElementType.constraint(translateConstraintDef(rule))
                    if (rule is Valid) context.valid()
                }
            }
        }
    }

    private fun ConstraintDef<*, *>.customMessage(message: String?): ConstraintDef<*, *> {
        message?.let {
            this.message(message)
        }
        return this
    }

    private fun translateConstraintDef(rule: ConstraintRule): ConstraintDef<*, *> = when (rule) {

        // hack: Valid is notNull
        is Valid -> ConstraintRuleTranslator<Valid> { NotNullDef().customMessage(it.message) }.translate(rule)
        is ValidByScript -> ConstraintRuleTranslator<ValidByScript> {
            ScriptAssertDef().lang(it.lang).script(it.script).alias(it.alias).reportOn(it.reportOn)
                .customMessage(it.message)
        }.translate(rule)

        is NotNull -> ConstraintRuleTranslator<NotNull> { NotNullDef().customMessage(it.message) }.translate(rule)
        is Null -> ConstraintRuleTranslator<Null> { NullDef().customMessage(it.message) }.translate(rule)

        is ArraySize -> ConstraintRuleTranslator<ArraySize> {
            SizeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)
        is ArrayNotEmpty -> ConstraintRuleTranslator<ArrayNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(
            rule
        )

        is ColSize -> ConstraintRuleTranslator<ColSize> {
            SizeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)
        is ColNotEmpty -> ConstraintRuleTranslator<ColNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(
            rule
        )

        is AssertTrue -> ConstraintRuleTranslator<AssertTrue> { AssertTrueDef().customMessage(it.message) }.translate(
            rule
        )
        is AssertFalse -> ConstraintRuleTranslator<AssertFalse> { AssertFalseDef().customMessage(it.message) }.translate(
            rule
        )

        is NotBlank -> ConstraintRuleTranslator<NotBlank> { NotBlankDef().customMessage(it.message) }.translate(rule)
        is CsNotEmpty -> ConstraintRuleTranslator<CsNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(rule)
        is CsSize -> ConstraintRuleTranslator<CsSize> {
            SizeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)
        is Regexp -> ConstraintRuleTranslator<Regexp> {
            PatternDef().regexp(it.regexp).customMessage(it.message)
        }.translate(rule)
        is Email -> ConstraintRuleTranslator<Email> { EmailDef().customMessage(it.message) }.translate(rule)
        is PhoneNumber -> ConstraintRuleTranslator<PhoneNumber> {
            PhoneNumberDef().regionCode(it.regionCode).customMessage(it.message)
        }.translate(rule)
        is InValues -> ConstraintRuleTranslator<InValues> {
            ValuesDef().values(it.values.toTypedArray()).customMessage(it.message)
        }.translate(rule)
        is CsMin -> ConstraintRuleTranslator<CsMin> { MinDef().value(it.value).customMessage(it.message) }.translate(
            rule
        )
        is CsMax -> ConstraintRuleTranslator<CsMax> { MaxDef().value(it.value).customMessage(it.message) }.translate(
            rule
        )
        is CsDecimalMin -> ConstraintRuleTranslator<CsDecimalMin> {
            DecimalMinDef().value(it.value).inclusive(it.inclusive).customMessage(it.message)
        }.translate(rule)
        is CsDecimalMax -> ConstraintRuleTranslator<CsDecimalMax> {
            DecimalMaxDef().value(it.value).inclusive(it.inclusive).customMessage(it.message)
        }.translate(rule)
        is CsPositive -> ConstraintRuleTranslator<CsPositive> { CsPositiveDef().customMessage(it.message) }.translate(
            rule
        )
        is CsPositiveOrZero -> ConstraintRuleTranslator<CsPositiveOrZero> { CsPositiveOrZeroDef().customMessage(it.message) }.translate(
            rule
        )
        is CsNegative -> ConstraintRuleTranslator<CsNegative> { CsNegativeDef().customMessage(it.message) }.translate(
            rule
        )
        is CsNegativeOrZero -> ConstraintRuleTranslator<CsNegativeOrZero> { CsNegativeOrZeroDef().customMessage(it.message) }.translate(
            rule
        )
        is CsRange -> ConstraintRuleTranslator<CsRange> {
            RangeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)
        is CsDigits -> ConstraintRuleTranslator<CsDigits> {
            DigitsDef().integer(it.integer).fraction(it.fraction).customMessage(it.message)
        }.translate(rule)
        is Iso8601Date -> ConstraintRuleTranslator<Iso8601Date> { Iso8601DateDef().customMessage(it.message) }.translate(
            rule
        )
        is InIso8601DateRange -> ConstraintRuleTranslator<InIso8601DateRange> {
            InIso8601DateRangeDef().startDate(it.startDate).stopDate(it.stopDate).customMessage(it.message)
        }.translate(rule)

        is NegativeOrZero -> ConstraintRuleTranslator<NegativeOrZero> { NegativeOrZeroDef().customMessage(it.message) }.translate(
            rule
        )
        is PositiveOrZero -> ConstraintRuleTranslator<PositiveOrZero> { PositiveOrZeroDef().customMessage(it.message) }.translate(
            rule
        )
        is Negative -> ConstraintRuleTranslator<Negative> { NegativeDef().customMessage(it.message) }.translate(rule)
        is Positive -> ConstraintRuleTranslator<Positive> { PositiveDef().customMessage(it.message) }.translate(rule)
        is Min -> ConstraintRuleTranslator<Min> { MinDef().value(it.value).customMessage(it.message) }.translate(rule)
        is Max -> ConstraintRuleTranslator<Max> { MaxDef().value(it.value).customMessage(it.message) }.translate(rule)
        is DecimalMin -> ConstraintRuleTranslator<DecimalMin> {
            DecimalMinDef().value(it.value).inclusive(it.inclusive).customMessage(it.message)
        }.translate(rule)
        is DecimalMax -> ConstraintRuleTranslator<DecimalMax> {
            DecimalMaxDef().value(it.value).inclusive(it.inclusive).customMessage(it.message)
        }.translate(rule)
        is Digits -> ConstraintRuleTranslator<Digits> {
            DigitsDef().integer(it.integer).fraction(it.fraction).customMessage(it.message)
        }.translate(rule)
        is Range -> ConstraintRuleTranslator<Range> {
            RangeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)

        is Future -> ConstraintRuleTranslator<Future> { FutureDef().customMessage(it.message) }.translate(rule)
        is FutureOrPresent -> ConstraintRuleTranslator<FutureOrPresent> { FutureOrPresentDef().customMessage(it.message) }.translate(
            rule
        )
        is Past -> ConstraintRuleTranslator<Past> { PastDef().customMessage(it.message) }.translate(rule)
        is PastOrPresent -> ConstraintRuleTranslator<PastOrPresent> { PastOrPresentDef().customMessage(it.message) }.translate(
            rule
        )
        is SubSetOf -> ConstraintRuleTranslator<SubSetOf> {
            SubSetDef().completeValues(it.values.toTypedArray()).customMessage(it.message)
        }.translate(rule)

        is MapHasKeys -> ConstraintRuleTranslator<MapHasKeys> {
            MapHasKeysDef().mapKeys(it.keys.toTypedArray()).customMessage(it.message)
        }.translate(rule)
        is MapNotEmpty -> ConstraintRuleTranslator<MapNotEmpty> { NotEmptyDef().customMessage(it.message) }.translate(
            rule
        )
        is MapSize -> ConstraintRuleTranslator<MapSize> {
            SizeDef().min(it.min).max(it.max).customMessage(it.message)
        }.translate(rule)
    }

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
