package com.capraro.kalidation.constraints.function

import com.capraro.kalidation.constraints.rule.*
import com.capraro.kalidation.spec.PropertyConstraint

/**
 * [CharSequence] Validation Functions.
 * @author Richard Capraro
 * @since 0.0.1
 */
fun PropertyConstraint<out Any, out CharSequence>.notBlank() {
    constraintRules.add(NotBlank())
}

fun PropertyConstraint<out Any, out CharSequence>.notEmpty() {
    constraintRules.add(CsNotEmpty())
}

fun PropertyConstraint<out Any, out CharSequence>.size(min: Int = 0, max: Int = Int.MAX_VALUE) {
    constraintRules.add(CsSize(min, max))
}

fun PropertyConstraint<out Any, out CharSequence>.regexp(regexp: String) {
    constraintRules.add(Regexp(regexp))
}

fun PropertyConstraint<out Any, out CharSequence>.email() {
    constraintRules.add(Email())
}

fun PropertyConstraint<out Any, out CharSequence>.phoneNumber(regionCode: String) {
    constraintRules.add(PhoneNumber(regionCode))
}

fun PropertyConstraint<out Any, out CharSequence>.inValues(vararg values: String) {
    constraintRules.add(InValues(values.asList()))
}

fun PropertyConstraint<out Any, out CharSequence>.min(value: Long) {
    constraintRules.add(CsMin(value))
}

fun PropertyConstraint<out Any, out CharSequence>.max(value: Long) {
    constraintRules.add(CsMax(value))
}

fun PropertyConstraint<out Any, out CharSequence>.decimalMin(value: String, inclusive: Boolean) {
    constraintRules.add(CsDecimalMin(value, inclusive))
}

fun PropertyConstraint<out Any, out CharSequence>.decimalMax(value: String, inclusive: Boolean) {
    constraintRules.add(CsDecimalMax(value, inclusive))
}

fun PropertyConstraint<out Any, out CharSequence>.digits(value: Int, fraction: Int) {
    constraintRules.add(CsDigits(value, fraction))
}