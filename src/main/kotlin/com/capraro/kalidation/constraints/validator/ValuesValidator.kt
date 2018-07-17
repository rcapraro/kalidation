package com.capraro.kalidation.constraints.validator

import com.capraro.kalidation.constraints.annotation.Values
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValuesValidator : ConstraintValidator<Values, CharSequence> {

    private var values = listOf<String>()

    override fun initialize(constraintAnnotation: Values) {
        values = constraintAnnotation.values.toList()
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }
        return values.contains(value)
    }
}