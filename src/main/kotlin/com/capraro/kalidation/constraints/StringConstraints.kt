package com.capraro.kalidation.constraints

import com.capraro.kalidation.spec.ConstraintRule
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * String constraints rules.
 * @author Richard Capraro
 * @since 0.0.1
 */

class StringNotBlank : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.notBlank() {
    constraintRules.add(StringNotBlank())
}

class StringNotEmpty : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.notEmpty() {
    constraintRules.add(StringNotEmpty())
}

data class StringSize(val min: Int, val max: Int) : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(StringSize(min, max))
}

data class Regexp(val regexp: String) : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.regexp(regexp: String) {
    constraintRules.add(Regexp(regexp))
}

class Email : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.email() {
    constraintRules.add(Email())
}

data class InValues(val values: List<String>) : ConstraintRule<String>

fun PropertyConstraint<out Any, String>.inValues(vararg values: String) {
    constraintRules.add(InValues(values.asList()))
}