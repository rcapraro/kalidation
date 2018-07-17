package com.capraro.kalidation.constraints

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Constraints rules on any classes.
 * @author Richard Capraro
 * @since 0.0.1
 */

class NotNull : ConstraintRule<Any>

fun PropertyConstraint<out Any, out Any?>.notNull() {
    constraintRules.add(NotNull())
}