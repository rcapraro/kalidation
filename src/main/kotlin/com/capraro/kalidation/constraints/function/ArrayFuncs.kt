package com.capraro.kalidation.constraints.function;

import com.capraro.kalidation.constraints.rule.ArrayNotEmpty
import com.capraro.kalidation.constraints.rule.ArraySize
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [Array] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Array<*>>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(ArraySize(min, max))
}

fun PropertyConstraint<out Any, out Array<*>>.notEmpty() {
    constraintRules.add(ArrayNotEmpty())
}