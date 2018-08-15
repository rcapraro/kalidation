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