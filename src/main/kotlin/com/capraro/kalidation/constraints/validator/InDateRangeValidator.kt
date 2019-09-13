package com.capraro.kalidation.constraints.validator

import com.capraro.kalidation.constraints.annotation.InDateRange
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * InDateRangeValidator validator.
 * @author Gwenael Cholet
 * @since 1.4.1
 */

class InDateRangeValidator : ConstraintValidator<InDateRange, String> {

    private var startDate = ""
    private var stopDate = ""

    override fun initialize(constraintAnnotation: InDateRange) {
        startDate = constraintAnnotation.startDate
        stopDate = constraintAnnotation.stopDate
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean = value?.let { date ->
        return try {
            val zonedDateTimeStartDate = ZonedDateTime.parse(startDate)
            val zonedDateTimeStopDate = ZonedDateTime.parse(stopDate)
            val zonedDateTimeDate = ZonedDateTime.parse(date)
            val a =  zonedDateTimeDate.isAfter(zonedDateTimeStartDate) && zonedDateTimeDate.isBefore(zonedDateTimeStopDate)
            return a
        } catch (e: DateTimeParseException) {
            false
        }
    } ?: true

}