package com.capraro.kalidation.spec

import arrow.data.Invalid
import arrow.data.Valid
import arrow.data.Validated
import com.capraro.kalidation.constraints.rule.ConstraintRule
import javax.validation.Validator
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Validation DSL Specification classes.
 * @author Richard Capraro
 * @since 0.0.1
 */

/**
 * Validation Spec class.
 * This 'root' class contains a list of [Constraint].
 */
data class ValidationSpec(val constraints: MutableList<Constraint<out Any>> = mutableListOf()) {
    internal lateinit var validator: Validator

    fun validate(constrainedClass: Any): Validated<Set<ValidationResult>, Boolean> {
        val validationSet = validator.validate(constrainedClass)
                .map {
                    ValidationResult(
                            fieldName = it.propertyPath.joinToString(".") { it.name },
                            message = it.message)
                }
                .toSet()

        return if (validationSet.isEmpty()) {
            Valid(true)
        } else {
            Invalid(validationSet)
        }
    }
}

/**
 * Class constraints.
 * A Class constraint refers to a class and contains a list of [PropertyConstraint]
 * @see PropertyConstraint
 */
data class Constraint<T : Any>(val constrainedClass: KClass<T>,
                               val propertyConstraints: MutableList<PropertyConstraint<T, out Any?>> = mutableListOf())

/**
 * Property constraints.
 * A Property constraint refers to a property and contains a list of [ConstraintRule] to apply to this property.
 * @see ConstraintRule
 */
data class PropertyConstraint<T : Any, P : Any?>(val constrainedProperty: KProperty1<T, P>,
                                                 val constraintRules: MutableList<ConstraintRule> = mutableListOf())

data class ValidationResult(val fieldName: String, val message: String)