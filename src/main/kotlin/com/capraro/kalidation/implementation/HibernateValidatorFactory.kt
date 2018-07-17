package com.capraro.kalidation.implementation

import com.capraro.kalidation.constraints.*
import com.capraro.kalidation.constraints.def.ValuesDef
import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.ValidationSpec
import org.hibernate.validator.HibernateValidator
import org.hibernate.validator.cfg.ConstraintDef
import org.hibernate.validator.cfg.ConstraintMapping
import org.hibernate.validator.cfg.defs.*
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping
import java.lang.annotation.ElementType
import javax.validation.Validation
import javax.validation.Validator
import kotlin.reflect.KClass

/**
 * Transforms a [ValidationSpec] into an Hibernate validator spec.
 * @see ValidationSpec
 * @author Richard Capraro
 * @since 0.0.1
 */
class HibernateValidatorFactory(private val spec: ValidationSpec) {

    private val constraints: MutableMap<KClass<out ConstraintRule<out Any>>, ConstraintRuleTranslator<out ConstraintRule<out Any>>> = mutableMapOf()

    fun <C : ConstraintRule<out Any>> registerCustomConstraint(constraintRuleClass: KClass<out C>, translator: ConstraintRuleTranslator<out C>) {
        constraints[constraintRuleClass] = translator
    }

    class ConstraintRuleTranslator<C : ConstraintRule<out Any>>(private val block: (C) -> ConstraintDef<*, *>) {
        fun translate(c: C): ConstraintDef<*, *> = block.invoke(c)
    }

    private fun createConstraintMapping(spec: ValidationSpec): ConstraintMapping {
        return with(spec) {
            val constraintMapping = DefaultConstraintMapping()
            constraints.forEach {
                val typeMapping = constraintMapping.type(it.constrainedClass.java)
                it.propertyConstraints.forEach {
                    val propertyMapping = typeMapping.property(it.constrainedProperty.name, ElementType.FIELD)
                    it.constraintRules.forEach { rule: ConstraintRule<out Any> ->
                        propertyMapping.constraint(createConstraintDef(rule))
                    }
                }
            }
            constraintMapping
        }
    }

    private fun <R : ConstraintRule<out Any>> createConstraintDef(rule: R): ConstraintDef<*, *> {

        return if (constraints.containsKey(rule::class)) {
            val translator = constraints[rule::class] as ConstraintRuleTranslator<R>
            translator.translate(rule)
        } else when (rule) {
            is NotNull -> ConstraintRuleTranslator<NotNull> { NotNullDef() }.translate(rule)
            is AssertTrue -> ConstraintRuleTranslator<AssertTrue> { AssertTrueDef() }.translate(rule)
            is AssertFalse -> ConstraintRuleTranslator<AssertFalse> { AssertFalseDef() }.translate(rule)
            is StringNotBlank -> ConstraintRuleTranslator<StringNotBlank> { NotBlankDef() }.translate(rule)
            is StringNotEmpty -> ConstraintRuleTranslator<StringNotEmpty> { NotEmptyDef() }.translate(rule)
            is StringSize -> ConstraintRuleTranslator<StringSize> { SizeDef().min(it.min).max(it.max) }.translate(rule)
            is InValues -> ConstraintRuleTranslator<InValues> { ValuesDef().values(it.values.toTypedArray()) }.translate(rule)
            is Min -> ConstraintRuleTranslator<Min> { MinDef().value(it.value.toLong()) }.translate(rule)
            is Max -> ConstraintRuleTranslator<Max> { MaxDef().value(it.value) }.translate(rule)
            is DecimalMin -> ConstraintRuleTranslator<DecimalMin> { DecimalMinDef().value(it.value).inclusive(it.inclusive) }.translate(rule)
            is DecimalMax -> ConstraintRuleTranslator<DecimalMax> { DecimalMaxDef().value(it.value).inclusive(it.inclusive) }.translate(rule)
            is Digits -> ConstraintRuleTranslator<Digits> { DigitsDef().integer(it.integer).fraction(it.fraction) }.translate(rule)
            is Future -> ConstraintRuleTranslator<Future> { FutureDef() }.translate(rule)
            is FutureOrPresent -> ConstraintRuleTranslator<FutureOrPresent> { FutureOrPresentDef() }.translate(rule)
            is Past -> ConstraintRuleTranslator<Past> { PastDef() }.translate(rule)
            is PastOrPresent -> ConstraintRuleTranslator<PastOrPresent> { PastOrPresentDef() }.translate(rule)
            else -> throw IllegalStateException("No Translator for the constraint ${rule.javaClass.canonicalName}")
        }
    }

    internal fun build(): Validator {
        val constraintMapping = createConstraintMapping(spec)
        val config = Validation.byProvider(HibernateValidator::class.java).configure()
        config.addMapping(constraintMapping)
        val factory = config.buildValidatorFactory()
        return factory.validator
    }
}