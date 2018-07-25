package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.AssertFalse
import com.capraro.kalidation.constraints.rule.AssertTrue
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Boolean Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, Boolean>.assertTrue() {
    constraintRules.add(AssertTrue())
}

fun PropertyConstraint<out Any, Boolean>.assertFalse() {
    constraintRules.add(AssertFalse())
}