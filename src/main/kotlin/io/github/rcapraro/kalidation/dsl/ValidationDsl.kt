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

package io.github.rcapraro.kalidation.dsl

import arrow.core.NonEmptyList
import arrow.core.toNonEmptyListOrNull
import io.github.rcapraro.kalidation.exception.KalidationException
import io.github.rcapraro.kalidation.implementation.HibernateValidatorFactory
import io.github.rcapraro.kalidation.spec.ClassConstraint
import io.github.rcapraro.kalidation.spec.ContainerElementType
import io.github.rcapraro.kalidation.spec.MethodConstraint
import io.github.rcapraro.kalidation.spec.PropertyConstraint
import io.github.rcapraro.kalidation.spec.ValidationSpec
import java.util.Locale
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1

/**
 * Validation DSL.
 * @author Richard Capraro
 * @since 0.0.1
 */

fun validationSpec(
    locale: Locale = Locale.getDefault(),
    messageBundle: String? = null,
    block: ValidationSpec.() -> Unit
): ValidationSpec {
    val validationSpec = ValidationSpec()
    block(validationSpec)
    validationSpec.validator = HibernateValidatorFactory(validationSpec).build(locale, messageBundle)
    return validationSpec
}

inline fun <reified T : Any> ValidationSpec.constraints(block: (@ValidationSpecMarker ClassConstraint<T>).() -> Unit) =
    this.constraints.add(ClassConstraint(T::class).apply(block))

fun <T : Any, P : Any?> ClassConstraint<T>.property(property: KProperty1<T, P>, block: (@ValidationSpecMarker PropertyConstraint<T, P>).() -> Unit) =
    this.propertyConstraints.add(PropertyConstraint(property).apply(block))

fun <T : Any, P : Collection<U?>, U : Any> PropertyConstraint<T, P>.eachElement(block: (@ValidationSpecMarker ContainerElementType<T, P, U>).() -> Unit) =
    this.containerElementsTypes.add(ContainerElementType<T, P, U>(this.constrainedProperty, listOf(0).toNonEmptyListOrNull()!!).apply(block))

fun <T : Any, P : Any?, U : Any> PropertyConstraint<T, P>.eachElement(
    type: KClass<U>,
    indexes: NonEmptyList<Int> = listOf(0).toNonEmptyListOrNull()!!,
    block: (@ValidationSpecMarker ContainerElementType<T, P, U>).() -> Unit
) = this.containerElementsTypes.add(ContainerElementType<T, P, U>(this.constrainedProperty, indexes).apply(block))

inline fun <reified T : Any, R : Any?> ClassConstraint<T>.returnOf(
    method: KFunction<R>,
    alias: String = "None",
    block: (@ValidationSpecMarker MethodConstraint<T, R>).() -> Unit
) {
    if (method.parameters.size > 1) { // first parameter is the return type
        throw KalidationException("Method ${method.name} should have 0 arguments", null)
    }
    this.methodConstraints.add(MethodConstraint(T::class, method, alias).apply(block))
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ValidationSpecMarker