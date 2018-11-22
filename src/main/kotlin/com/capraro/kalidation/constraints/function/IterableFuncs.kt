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

import com.capraro.kalidation.constraints.rule.IterableNotEmpty
import com.capraro.kalidation.constraints.rule.IterableSize
import com.capraro.kalidation.constraints.rule.SubSetOf
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [Iterable] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Iterable<*>?>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(IterableSize(min, max))
}

fun PropertyConstraint<out Any, out Iterable<*>?>.notEmpty() {
    constraintRules.add(IterableNotEmpty())
}

fun PropertyConstraint<out Any, out Iterable<*>?>.subSetOf(vararg completeValues: String) {
    constraintRules.add(SubSetOf(completeValues.asList()))
}

