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
import arrow.data.NonEmptyList
import arrow.data.Valid
import arrow.data.Validated
import com.capraro.kalidation.constraints.rule.ConstraintRule
import javax.validation.Validator
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaMethod

/**
 * Validation DSL Specification classes.
 * @author Richard Capraro
 * @since 0.0.1
 */

/**
 * Validation Spec class.
 * This 'root' class contains a list of [ClassConstraint].
 */
data class ValidationSpec(val constraints: MutableList<ClassConstraint<out Any>> = mutableListOf()) {
    internal lateinit var validator: Validator

    fun validate(constrainedClass: Any): Validated<Set<ValidationResult>, Boolean> {

        val validationResult = validator.validate(constrainedClass)

        constraints.forEach { constraint ->
            constraint.methodConstraints.forEach {
                validationResult.addAll(validator
                        .forExecutables()
                        .validateReturnValue(constrainedClass, it.constrainedMethod.javaMethod, it.constrainedMethod.call(constrainedClass)))
            }
        }

        val validationSet = validationResult
                .map {
                    ValidationResult(
                            fieldName = it.propertyPath.joinToString(".") { violation -> violation.name },
                            invalidValue = it.invalidValue,
                            messageTemplate = it.messageTemplate,
                            message = it.message)
                }
                .sortedBy { it.fieldName }
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
data class ClassConstraint<T : Any>(val constrainedClass: KClass<T>,
                                    val propertyConstraints: MutableList<PropertyConstraint<T, out Any?>> = mutableListOf(),
                                    val methodConstraints: MutableList<MethodConstraint<out Any?>> = mutableListOf()
)

open class Constraint<T : Any, P : Any?>(val constraintRules: MutableList<ConstraintRule> = mutableListOf())

/**
 * Property constraints.
 * A Property constraint refers to a property and contains a list of [ConstraintRule] to apply to this property.
 * @see ConstraintRule
 */
data class PropertyConstraint<T : Any, P : Any?>(val constrainedProperty: KProperty1<T, P>, val containerElementsTypes: MutableList<ContainerElementType<T, Any>> = mutableListOf()) : Constraint<T, P>()

/**
 * ContainerElementType.
 */
data class ContainerElementType<T : Any, U : Any>(
        val indexes: NonEmptyList<Int>
) : Constraint<T, U>()

/**
 * Method return value constraints.
 * A Method return value constraint refers to the return value of a method and contains a list of [ConstraintRule] to apply to this return value.
 * @see ConstraintRule
 */
data class MethodConstraint<R : Any?>(val constrainedMethod: KFunction<R>) : Constraint<Any, R>()

data class ValidationResult(val fieldName: String, val invalidValue: Any?, val messageTemplate: String, val message: String)