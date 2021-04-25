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

import io.github.rcapraro.kalidation.constraints.rule.NotNull
import io.github.rcapraro.kalidation.constraints.rule.Null
import io.github.rcapraro.kalidation.constraints.rule.Valid
import io.github.rcapraro.kalidation.constraints.rule.ValidByScript
import io.github.rcapraro.kalidation.spec.Constraint

/**
 * Validation Functions on [Any] classes.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun Constraint<out Any, out Any?>.notNull(message: String? = null) {
    constraintRules.add(NotNull(message))
}

fun Constraint<out Any, out Any?>.isNull(message: String? = null) {
    constraintRules.add(Null(message))
}

fun Constraint<out Any, out Any?>.valid(message: String? = null) {
    constraintRules.add(Valid(message))
}

fun Constraint<out Any, out Any?>.validByScript(
    lang: String,
    script: String,
    alias: String = "_this",
    reportOn: String = "",
    message: String? = null
) {
    constraintRules.add(ValidByScript(lang, script, alias, reportOn, message))
}
