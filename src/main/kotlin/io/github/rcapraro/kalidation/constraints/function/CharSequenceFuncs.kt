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

package io.github.rcapraro.kalidation.constraints.function

import io.github.rcapraro.kalidation.constraints.rule.CsDecimalMax
import io.github.rcapraro.kalidation.constraints.rule.CsDecimalMin
import io.github.rcapraro.kalidation.constraints.rule.CsDigits
import io.github.rcapraro.kalidation.constraints.rule.CsMax
import io.github.rcapraro.kalidation.constraints.rule.CsMin
import io.github.rcapraro.kalidation.constraints.rule.CsNegative
import io.github.rcapraro.kalidation.constraints.rule.CsNegativeOrZero
import io.github.rcapraro.kalidation.constraints.rule.CsNotEmpty
import io.github.rcapraro.kalidation.constraints.rule.CsPositive
import io.github.rcapraro.kalidation.constraints.rule.CsPositiveOrZero
import io.github.rcapraro.kalidation.constraints.rule.CsRange
import io.github.rcapraro.kalidation.constraints.rule.CsSize
import io.github.rcapraro.kalidation.constraints.rule.Email
import io.github.rcapraro.kalidation.constraints.rule.InIso8601DateRange
import io.github.rcapraro.kalidation.constraints.rule.InValues
import io.github.rcapraro.kalidation.constraints.rule.Iso8601Date
import io.github.rcapraro.kalidation.constraints.rule.NotBlank
import io.github.rcapraro.kalidation.constraints.rule.PhoneNumber
import io.github.rcapraro.kalidation.constraints.rule.Regexp
import io.github.rcapraro.kalidation.spec.Constraint

/**
 * [CharSequence] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun Constraint<out Any, out CharSequence?>.notBlank(message: String? = null) {
    constraintRules.add(NotBlank(message))
}

fun Constraint<out Any, out CharSequence?>.notEmpty(message: String? = null) {
    constraintRules.add(CsNotEmpty(message))
}

fun Constraint<out Any, out CharSequence?>.size(min: Int = 0, max: Int = Int.MAX_VALUE, message: String? = null) {
    constraintRules.add(CsSize(min, max, message))
}

fun Constraint<out Any, out CharSequence?>.regexp(regexp: String, message: String? = null) {
    constraintRules.add(Regexp(regexp, message))
}

fun Constraint<out Any, out CharSequence?>.email(message: String? = null) {
    constraintRules.add(Email(message))
}

fun Constraint<out Any, out CharSequence?>.phoneNumber(regionCode: String, message: String? = null) {
    constraintRules.add(PhoneNumber(regionCode, message))
}

fun Constraint<out Any, out CharSequence?>.inValues(vararg values: String, message: String? = null) {
    constraintRules.add(InValues(values.asList(), message))
}

fun Constraint<out Any, out CharSequence?>.range(min: Long, max: Long, message: String? = null) {
    constraintRules.add(CsRange(min, max, message))
}

fun Constraint<out Any, out CharSequence?>.positive(message: String? = null) {
    constraintRules.add(CsPositive(message))
}

fun Constraint<out Any, out CharSequence?>.positiveOrZero(message: String? = null) {
    constraintRules.add(CsPositiveOrZero(message))
}

fun Constraint<out Any, out CharSequence?>.negative(message: String? = null) {
    constraintRules.add(CsNegative(message))
}

fun Constraint<out Any, out CharSequence?>.negativeOrZero(message: String? = null) {
    constraintRules.add(CsNegativeOrZero(message))
}

fun Constraint<out Any, out CharSequence?>.min(value: Long, message: String? = null) {
    constraintRules.add(CsMin(value, message))
}

fun Constraint<out Any, out CharSequence?>.max(value: Long, message: String? = null) {
    constraintRules.add(CsMax(value, message))
}

fun Constraint<out Any, out CharSequence?>.decimalMin(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(CsDecimalMin(value, inclusive, message))
}

fun Constraint<out Any, out CharSequence?>.decimalMax(value: String, inclusive: Boolean, message: String? = null) {
    constraintRules.add(CsDecimalMax(value, inclusive, message))
}

fun Constraint<out Any, out CharSequence?>.digits(value: Int, fraction: Int, message: String? = null) {
    constraintRules.add(CsDigits(value, fraction, message))
}

fun Constraint<out Any, out CharSequence?>.iso8601Date(message: String? = null) {
    constraintRules.add(Iso8601Date(message))
}

/**
 * Control that an Iso8601Date is ranged.
 *
 * @receiver Constraint<out Any, out CharSequence?>
 * @param startDate String. Beginning date of the range
 * @param stopDate String. End date of the range
 * @param message String?. Error message answered
 */
fun Constraint<out Any, out CharSequence?>.inIso8601DateRange(
    startDate: String,
    stopDate: String,
    message: String? = null
) {
    constraintRules.add(InIso8601DateRange(startDate, stopDate, message))
}
