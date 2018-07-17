package com.capraro.kalidation.spec

import arrow.data.Invalid
import arrow.data.Valid
import arrow.data.Validated
import javax.validation.ConstraintViolation
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

    fun validate(constrainedClass: Any): Validated<Set<ConstraintViolation<Any>>, Boolean> {
        val set = validator.validate(constrainedClass)
        return if (set.size == 0) {
            Valid(true)
        } else {
            Invalid(set)
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
                                                 val constraintRules: MutableList<ConstraintRule<*>> = mutableListOf())

/**
 * Interface for all the [ConstraintRule] implementations.
 */
interface ConstraintRule<P : Any>