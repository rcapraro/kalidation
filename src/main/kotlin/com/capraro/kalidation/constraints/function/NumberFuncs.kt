package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.*
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [Number] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out Number>.negativeOrZero() {
    constraintRules.add(NegativeOrZero())
}

fun PropertyConstraint<out Any, out Number>.positiveOrZero() {
    constraintRules.add(PositiveOrZero())
}

fun PropertyConstraint<out Any, out Number>.negative() {
    constraintRules.add(Negative())
}

fun PropertyConstraint<out Any, out Number>.positive() {
    constraintRules.add(Positive())
}

fun PropertyConstraint<out Any, out Number>.min(value: Long) {
    constraintRules.add(Min(value))
}

fun PropertyConstraint<out Any, out Number>.max(value: Long) {
    constraintRules.add(Max(value))
}

fun PropertyConstraint<out Any, out Number>.decimalMin(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMin(value, inclusive))
}

fun PropertyConstraint<out Any, out Number>.decimalMax(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMax(value, inclusive))
}

fun PropertyConstraint<out Any, out Number>.digits(integer: Int = 0, fraction: Int = 0) {
    constraintRules.add(Digits(integer, fraction))
}