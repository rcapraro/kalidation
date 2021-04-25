/*
 * The MIT License
 *
 * Copyright (c) 2018 Richard Capraro
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.rcapraro.kalidation.constraints.validator

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import io.github.rcapraro.kalidation.constraints.annotation.PhoneNumber
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * PhoneNumber validator.
 * @author Richard Capraro
 * @since 0.0.1
 */
class PhoneNumberValidator : ConstraintValidator<PhoneNumber, CharSequence> {

    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    private lateinit var regionCode: String

    override fun initialize(constraintAnnotation: PhoneNumber) {
        regionCode = constraintAnnotation.regionCode
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean = value?.let {

        return try {
            phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(value, regionCode))
        } catch (e: NumberParseException) {
            false
        }
    } ?: true
}
