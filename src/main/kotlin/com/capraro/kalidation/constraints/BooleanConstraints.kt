package com.capraro.kalidation.constraints

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Boolean constraints rules.
 * @author Richard Capraro
 * @since 0.0.1
 */

class AssertTrue : ConstraintRule<Boolean>

fun PropertyConstraint<out Any, Boolean>.assertTrue() {
    constraintRules.add(AssertTrue())
}

class AssertFalse : ConstraintRule<Boolean>

fun PropertyConstraint<out Any, Boolean>.assertFalse() {
    constraintRules.add(AssertFalse())
}