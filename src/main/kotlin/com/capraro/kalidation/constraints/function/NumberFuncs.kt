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

package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.*
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [Number] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Number>.negativeOrZero() {
    constraintRules.add(NegativeOrZero())
}

fun PropertyConstraint<out Any, out Number>.positiveOrZero() {
    constraintRules.add(PositiveOrZero())
}

fun PropertyConstraint<out Any, out Number>.negative() {
    constraintRules.add(Negative())
}

fun PropertyConstraint<out Any, out Number>.positive() {
    constraintRules.add(Positive())
}

fun PropertyConstraint<out Any, out Number>.min(value: Long) {
    constraintRules.add(Min(value))
}

fun PropertyConstraint<out Any, out Number>.max(value: Long) {
    constraintRules.add(Max(value))
}

fun PropertyConstraint<out Any, out Number>.decimalMin(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMin(value, inclusive))
}

fun PropertyConstraint<out Any, out Number>.decimalMax(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMax(value, inclusive))
}

fun PropertyConstraint<out Any, out Number>.digits(integer: Int = 0, fraction: Int = 0) {
    constraintRules.add(Digits(integer, fraction))
}