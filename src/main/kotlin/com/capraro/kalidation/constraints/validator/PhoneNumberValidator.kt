package com.capraro.kalidation.constraints.validator

import com.capraro.kalidation.constraints.annotation.PhoneNumber
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * PhoneNumber validator.
 * @author Richard Capraro
 * @since 0.0.1
 */
class PhoneNumberValidator : ConstraintValidator<PhoneNumber, CharSequence> {

    val phoneNumberUtil = PhoneNumberUtil.getInstance()

    private lateinit var regionCode: String

    override fun initialize(constraintAnnotation: PhoneNumber) {
        regionCode = constraintAnnotation.regionCode
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        return try {
            phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(value, regionCode))
        } catch (e: NumberParseException) {
            false
        }
    }
}