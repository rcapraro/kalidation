package com.capraro.kalidation.constraints.annotation

import com.capraro.kalidation.constraints.validator.PhoneNumberValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PhoneNumberValidator::class])
@Target(
        AnnotationTarget.FIELD,
        AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class PhoneNumber(val message: String = "{javax.validation.constraints.PhoneNumber.message}",
                             val groups: Array<KClass<out Any>> = [],
                             val payload: Array<KClass<out Any>> = [],
                             val regionCode: String)