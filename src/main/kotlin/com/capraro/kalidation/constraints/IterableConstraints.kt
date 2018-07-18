package com.capraro.kalidation.constraints;

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Number constraints rules.
 *
 * @author Richard Capraro
 * @since 0.0.1
 */

class IterableSize(val min: Int, val max: Int) : ConstraintRule<IterableSize>

fun PropertyConstraint<out Any, out Iterable<*>>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(StringSize(min, max))
}
