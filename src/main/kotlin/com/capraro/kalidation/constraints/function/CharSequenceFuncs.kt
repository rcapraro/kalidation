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
 * [CharSequence] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out CharSequence?>.notBlank() {
    constraintRules.add(NotBlank())
}

fun PropertyConstraint<out Any, out CharSequence?>.notEmpty() {
    constraintRules.add(CsNotEmpty())
}

fun PropertyConstraint<out Any, out CharSequence?>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(CsSize(min, max))
}

fun PropertyConstraint<out Any, out CharSequence?>.regexp(regexp: String) {
    constraintRules.add(Regexp(regexp))
}

fun PropertyConstraint<out Any, out CharSequence?>.email() {
    constraintRules.add(Email())
}

fun PropertyConstraint<out Any, out CharSequence?>.phoneNumber(regionCode: String) {
    constraintRules.add(PhoneNumber(regionCode))
}

fun PropertyConstraint<out Any, out CharSequence?>.inValues(vararg values: String) {
    constraintRules.add(InValues(values.asList()))
}

fun PropertyConstraint<out Any, out CharSequence?>.min(value: Long) {
    constraintRules.add(CsMin(value))
}

fun PropertyConstraint<out Any, out CharSequence?>.max(value: Long) {
    constraintRules.add(CsMax(value))
}

fun PropertyConstraint<out Any, out CharSequence?>.decimalMin(value: String, inclusive: Boolean) {
    constraintRules.add(CsDecimalMin(value, inclusive))
}

fun PropertyConstraint<out Any, out CharSequence?>.decimalMax(value: String, inclusive: Boolean) {
    constraintRules.add(CsDecimalMax(value, inclusive))
}

fun PropertyConstraint<out Any, out CharSequence?>.digits(value: Int, fraction: Int) {
    constraintRules.add(CsDigits(value, fraction))
}

fun PropertyConstraint<out Any, out CharSequence?>.dateValid() {
    constraintRules.add(CsDateValid())
}