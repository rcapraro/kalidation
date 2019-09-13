package com.capraro.kalidation.constraints.def

import com.capraro.kalidation.constraints.annotation.InIso8601DateRange
import org.hibernate.validator.cfg.ConstraintDef

/**
 * Range date validation definition.
 * @author Gwenael Cholet
 * @since 1.4.1
 */
class InIso8601DateRangeDef : ConstraintDef<InIso8601DateRangeDef, InIso8601DateRange>(InIso8601DateRange::class.java) {

    /**
     * Setter of the start date of tha range
     *
     * @param startDate String. Beginning date of the range
     * @return InDateRangeDef
     */
    fun startDate(startDate: String): InIso8601DateRangeDef {
        addParameter("startDate", startDate)
        return this
    }

    /**
     * Setter of the stop date of tha range
     *
     * @param stopDate String. End date of the range
     * @return InDateRangeDef
     */
    fun stopDate(stopDate: String): InIso8601DateRangeDef {
        addParameter("stopDate", stopDate)
        return this
    }
}
