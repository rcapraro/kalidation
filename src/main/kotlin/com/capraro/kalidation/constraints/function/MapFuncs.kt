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

import com.capraro.kalidation.constraints.rule.MapHasKeys
import com.capraro.kalidation.constraints.rule.MapNotEmpty
import com.capraro.kalidation.constraints.rule.MapSize
import com.capraro.kalidation.spec.Constraint

/**
 * [Map] Validation Functions.
 * @author Richard Capraro
 * @since 1.2.8
 */
fun Constraint<out Any, out Map<String, *>?>.size(min: Int = 0, max: Int = Int.MAX_VALUE, message: String? = null) {
    constraintRules.add(MapSize(min, max, message))
}

fun Constraint<out Any, out Map<String, *>?>.notEmpty(message: String? = null) {
    constraintRules.add(MapNotEmpty(message))
}

fun Constraint<out Any, out Map<String, *>?>.hasKeys(vararg mapKeys: String, message: String? = null) {
    constraintRules.add(MapHasKeys(mapKeys.asList(), message))
}
