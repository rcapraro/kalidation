package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.NotNull
import com.capraro.kalidation.constraints.rule.Null
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Validation Functions on any classes.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Any?>.notNull() {
    constraintRules.add(NotNull())
}

fun PropertyConstraint<out Any, out Any?>.isNull() {
    constraintRules.add(Null())
}