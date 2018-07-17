package com.capraro.kalidation.constraints

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint
import java.time.temporal.Temporal

/**
 * Temporal constraints rules.
 *
 * @author Richard Capraro
 * @since 0.0.1
 */

class Future : ConstraintRule<Temporal>

fun PropertyConstraint<out Any, out Temporal>.future() {
    constraintRules.add(Future())
}

class FutureOrPresent : ConstraintRule<Temporal>

fun PropertyConstraint<out Any, out Temporal>.futureOrPresent() {
    constraintRules.add(FutureOrPresent())
}

class Past : ConstraintRule<Temporal>

fun PropertyConstraint<out Any, out Temporal>.past() {
    constraintRules.add(Past())
}

class PastOrPresent : ConstraintRule<Temporal>

fun PropertyConstraint<out Any, out Temporal>.pastOrPresent() {
    constraintRules.add(PastOrPresent())
}
