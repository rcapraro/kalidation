package com.capraro.kalidation.constraints.def

import com.capraro.kalidation.constraints.annotation.InDateRange
import org.hibernate.validator.cfg.ConstraintDef

/**
 * Range date validation definition.
 * @author Gwenael Cholet
 * @since 1.4.1
 */
class InDateRangeDef : ConstraintDef<InDateRangeDef, InDateRange>(InDateRange::class.java) {
    fun startDate(startDate: String): InDateRangeDef {
        addParameter("startDate", startDate)
        return this
    }

    fun stopDate(stopDate: String): InDateRangeDef {
        addParameter("stopDate", stopDate)
        return this
    }
}
