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

package com.capraro.kalidation.dsl

import com.capraro.kalidation.exception.KalidationException
import com.capraro.kalidation.implementation.HibernateValidatorFactory
import com.capraro.kalidation.spec.Constraint
import com.capraro.kalidation.spec.MethodConstraint
import com.capraro.kalidation.spec.PropertyConstraint
import com.capraro.kalidation.spec.ValidationSpec
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1

/**
 * Validation DSL.
 * @author Richard Capraro
 * @since 0.0.1
 */

fun validationSpec(locale: Locale = Locale.getDefault(), messageBundle: String? = null, block: ValidationSpec.() -> Unit): ValidationSpec {
    val validationSpec = ValidationSpec()
    block(validationSpec)
    validationSpec.validator = HibernateValidatorFactory(validationSpec).build(locale, messageBundle)
    return validationSpec
}

inline fun <reified T : Any> ValidationSpec.constraints(block: (@ValidationSpecMarker Constraint<T>).() -> Unit) {
    val constraints = Constraint(T::class)
    this.constraints.add(constraints)
    block(constraints)
}

fun <T : Any, P : Any?> Constraint<T>.property(property: KProperty1<T, P>, block: (@ValidationSpecMarker PropertyConstraint<T, P>).() -> Unit) {
    val propertyConstraint = PropertyConstraint(property)
    this.propertyConstraints.add(propertyConstraint)
    block(propertyConstraint)
}

fun <T : Any, R : Any?> Constraint<T>.returnOf(method: KFunction<R>, block: (@ValidationSpecMarker MethodConstraint<R>).() -> Unit) {
    if (method.parameters.size > 1) { //first parameter is the return type
        throw KalidationException("Method ${method.name} should have 0 arguments", null)
    }
    val methodConstraint = MethodConstraint(method)
    this.methodConstraints.add(methodConstraint)
    block(methodConstraint)
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ValidationSpecMarker
