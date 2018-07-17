package com.capraro.kalidation.dsl

import com.capraro.kalidation.implementation.HibernateValidatorFactory
import com.capraro.kalidation.spec.Constraint
import com.capraro.kalidation.spec.PropertyConstraint
import com.capraro.kalidation.spec.ValidationSpec
import kotlin.reflect.KProperty1

/**
 * Validation DSL.
 * @author Richard Capraro
 * @since 0.0.1
 */

fun validationSpec(block: ValidationSpec.() -> Unit): ValidationSpec {
    val validationSpec = ValidationSpec()
    block(validationSpec)
    validationSpec.validator = HibernateValidatorFactory(validationSpec).build()
    return validationSpec
}

inline fun <reified T : Any> ValidationSpec.constraints(block: Constraint<T>.() -> Unit) {
    val constraints = Constraint(T::class)
    this.constraints.add(constraints)
    block(constraints)
}

fun <T : Any, P : Any?> Constraint<T>.property(property: KProperty1<T, P>, block: PropertyConstraint<T, P>.() -> Unit) {
    val propertyConstraint = PropertyConstraint(property)
    this.propertyConstraints.add(propertyConstraint)
    block(propertyConstraint)
}