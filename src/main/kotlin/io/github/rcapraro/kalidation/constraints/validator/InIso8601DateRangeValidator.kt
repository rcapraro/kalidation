package io.github.rcapraro.kalidation.constraints.validator

import io.github.rcapraro.kalidation.constraints.annotation.InIso8601DateRange
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

/**
 * InDateRangeValidator validator.
 * @author Gwenael Cholet
 * @since 1.4.1
 */

class InIso8601DateRangeValidator : ConstraintValidator<InIso8601DateRange, String> {

    private var startDate = ""
    private var stopDate = ""

    override fun initialize(constraintAnnotation: InIso8601DateRange) {
        startDate = constraintAnnotation.startDate
        stopDate = constraintAnnotation.stopDate
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean = value?.let { date ->
        return try {
            val zonedDateTimeStartDate = ZonedDateTime.parse(startDate)
            val zonedDateTimeStopDate = ZonedDateTime.parse(stopDate)
            val zonedDateTimeDate = ZonedDateTime.parse(date)
            return when {
                zonedDateTimeDate.isEqual(zonedDateTimeStartDate) || zonedDateTimeDate.isEqual(zonedDateTimeStopDate) -> true
                else -> zonedDateTimeDate.isAfter(zonedDateTimeStartDate) && zonedDateTimeDate.isBefore(
                    zonedDateTimeStopDate
                )
            }
        } catch (e: DateTimeParseException) {
            false
        }
    } ?: true
}