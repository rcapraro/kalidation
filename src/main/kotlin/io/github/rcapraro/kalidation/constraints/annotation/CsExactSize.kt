package io.github.rcapraro.kalidation.constraints.annotation

import io.github.rcapraro.kalidation.constraints.validator.CsExactSizeValidator
import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [CsExactSizeValidator::class])
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsExactSize(
    val message: String = "{jakarta.validation.constraints.ExactSize.message}",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = [],
    val size: Int
)