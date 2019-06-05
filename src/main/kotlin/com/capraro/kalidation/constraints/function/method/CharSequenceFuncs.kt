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
 * [CharSequence] Validation Functions.
 * @author Richard Capraro
 * @since 1.3.0
 */
fun MethodConstraint<out CharSequence?>.notBlank(message: String? = null) {
    constraintRules.add(NotBlank(message))
}

fun MethodConstraint<out CharSequence?>.notEmpty(message: String? = null) {
    constraintRules.add(CsNotEmpty(message))
}

fun MethodConstraint<out CharSequence?>.size(min: Int = 0, max: Int = Int.MAX_VALUE, message: String? = null) {
    constraintRules.add(CsSize(min, max, message))
}

fun MethodConstraint<out CharSequence?>.regexp(regexp: String, message: String? = null) {
    constraintRules.add(Regexp(regexp, message))
}

fun MethodConstraint<out CharSequence?>.email(message: String? = null) {
    constraintRules.add(Email(message))
}

fun MethodConstraint<out CharSequence?>.phoneNumber(regionCode: String, message: String? = null) {
    constraintRules.add(PhoneNumber(regionCode, message))
}

fun MethodConstraint<out CharSequence?>.inValues(vararg values: String, message: String? = null) {
    constraintRules.add(InValues(values.asList(), message))
}

fun MethodConstraint<out CharSequence?>.range(min: Long, max: Long, message: String? = null) {
    constraintRules.add(CsRange(min, max, message))
}

fun MethodConstraint<out CharSequence?>.positive(message: String? = null) {
    constraintRules.add(CsPositive(message))
}

fun MethodConstraint<out CharSequence?>.positiveOrZero(message: String? = null) {
    constraintRules.add(CsPositiveOrZero(message))
}

fun MethodConstraint<out CharSequence?>.negative(message: String? = null) {
    constraintRules.add(CsNegative(message))
}

fun MethodConstraint<out CharSequence?>.negativeOrZero(message: String? = null) {
    constraintRules.add(CsNegativeOrZero(message))
}

fun MethodConstraint<out CharSequence?>.min(value: Long, message: String? = null) {
    constraintRules.add(CsMin(value, message))
}

fun MethodConstraint<out CharSequence?>.max(value: Long, message: String? = null) {
    constraintRules.add(CsMax(value, message))
}

fun MethodConstraint<out CharSequence?>.decimalMin(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(CsDecimalMin(value, inclusive, message))
}

fun MethodConstraint<out CharSequence?>.decimalMax(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(CsDecimalMax(value, inclusive, message))
}

fun MethodConstraint<out CharSequence?>.digits(value: Int, fraction: Int, message: String? = null) {
    constraintRules.add(CsDigits(value, fraction, message))
}

fun MethodConstraint<out CharSequence?>.iso8601Date(message: String? = null) {
    constraintRules.add(Iso8601Date(message))
}