package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.Future
import com.capraro.kalidation.constraints.rule.FutureOrPresent
import com.capraro.kalidation.constraints.rule.Past
import com.capraro.kalidation.constraints.rule.PastOrPresent
import com.capraro.kalidation.spec.PropertyConstraint
import java.time.temporal.Temporal

/**
 * [Temporal] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Temporal>.future() {
    constraintRules.add(Future())
}

fun PropertyConstraint<out Any, out Temporal>.futureOrPresent() {
    constraintRules.add(FutureOrPresent())
}

fun PropertyConstraint<out Any, out Temporal>.past() {
    constraintRules.add(Past())
}

fun PropertyConstraint<out Any, out Temporal>.pastOrPresent() {
    constraintRules.add(PastOrPresent())
}
