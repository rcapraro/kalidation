package com.capraro.kalidation.constraints.function;

import com.capraro.kalidation.constraints.rule.IterableNotEmpty
import com.capraro.kalidation.constraints.rule.IterableSize
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [Iterable] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Iterable<*>>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(IterableSize(min, max))
}

fun PropertyConstraint<out Any, out Iterable<*>>.notEmpty() {
    constraintRules.add(IterableNotEmpty())
}

