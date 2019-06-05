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

package com.capraro.kalidation.constraints.function.method

import com.capraro.kalidation.constraints.rule.*
import com.capraro.kalidation.spec.MethodConstraint

/**
 * [Number] Validation Functions.
 * @author Richard Capraro
 * @since 1.3.0
 */
fun MethodConstraint<out Number?>.range(min: Long, max: Long, message: String? = null) {
    constraintRules.add(Range(min, max, message))
}

fun MethodConstraint<out Number?>.negativeOrZero(message: String? = null) {
    constraintRules.add(NegativeOrZero(message))
}

fun MethodConstraint<out Number?>.positiveOrZero(message: String? = null) {
    constraintRules.add(PositiveOrZero(message))
}

fun MethodConstraint<out Number?>.negative(message: String? = null) {
    constraintRules.add(Negative(message))
}

fun MethodConstraint<out Number?>.positive(message: String? = null) {
    constraintRules.add(Positive(message))
}

fun MethodConstraint<out Number?>.min(value: Long, message: String? = null) {
    constraintRules.add(Min(value, message))
}

fun MethodConstraint<out Number?>.max(value: Long, message: String? = null) {
    constraintRules.add(Max(value, message))
}

fun MethodConstraint<out Number?>.decimalMin(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(DecimalMin(value, inclusive, message))
}

fun MethodConstraint<out Number?>.decimalMax(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(DecimalMax(value, inclusive, message))
}

fun MethodConstraint<out Number?>.digits(integer: Int = 0, fraction: Int = 0, message: String? = null) {
    constraintRules.add(Digits(integer, fraction, message))
}