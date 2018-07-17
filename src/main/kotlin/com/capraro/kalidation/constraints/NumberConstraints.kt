package com.capraro.kalidation.constraints

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * Number constraints rules.
 * @author Richard Capraro
 * @since 0.0.1
 */

class Min(val value: Number) : ConstraintRule<Number>

fun PropertyConstraint<out Any, out Number>.min(value: Long) {
    constraintRules.add(Min(value))
}

class Max(val value: Long) : ConstraintRule<Number>

fun PropertyConstraint<out Any, out Number>.max(value: Long) {
    constraintRules.add(Max(value))
}

class DecimalMin(val value: String, val inclusive: Boolean) : ConstraintRule<Number>

fun PropertyConstraint<out Any, out Number>.decimalMin(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMin(value, inclusive))
}

class DecimalMax(val value: String, val inclusive: Boolean) : ConstraintRule<Number>

fun PropertyConstraint<out Any, out Number>.decimalMax(value: String, inclusive: Boolean) {
    constraintRules.add(DecimalMax(value, inclusive))
}

class Digits(val integer: Int, val fraction: Int) : ConstraintRule<Number>

fun PropertyConstraint<out Any, out Number>.digits(integer: Int = 0, fraction: Int = 0) {
    constraintRules.add(Digits(integer, fraction))
}