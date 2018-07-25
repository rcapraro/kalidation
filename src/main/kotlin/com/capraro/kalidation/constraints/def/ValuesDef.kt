package com.capraro.kalidation.constraints.def

import com.capraro.kalidation.constraints.annotation.Values
import org.hibernate.validator.cfg.ConstraintDef

/**
 * Values validation definition
 * @author Richard Capraro
 * @since 0.0.1
 */
class ValuesDef : ConstraintDef<ValuesDef, Values>(Values::class.java) {

    fun values(values: Array<out String>): ValuesDef {
        addParameter("values", values)
        return this
    }

}