package com.capraro.kalidation.constraints.annotation

import com.capraro.kalidation.constraints.validator.ValuesValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ValuesValidator::class])
@Target(
        AnnotationTarget.FIELD,
        AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class Values(val message: String = "{org.hibernate.validator.constraints.Values.message}",
                        val groups: Array<KClass<out Any>> = [],
                        val payload: Array<KClass<out Any>> = [],
                        val values: Array<String>)