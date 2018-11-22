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

import com.capraro.kalidation.constraints.rule.Future
import com.capraro.kalidation.constraints.rule.FutureOrPresent
import com.capraro.kalidation.constraints.rule.Past
import com.capraro.kalidation.constraints.rule.PastOrPresent
import com.capraro.kalidation.spec.PropertyConstraint
import java.time.temporal.Temporal

/**
 * [Temporal] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Temporal?>.future() {
    constraintRules.add(Future())
}

fun PropertyConstraint<out Any, out Temporal?>.futureOrPresent() {
    constraintRules.add(FutureOrPresent())
}

fun PropertyConstraint<out Any, out Temporal?>.past() {
    constraintRules.add(Past())
}

fun PropertyConstraint<out Any, out Temporal?>.pastOrPresent() {
    constraintRules.add(PastOrPresent())
}
