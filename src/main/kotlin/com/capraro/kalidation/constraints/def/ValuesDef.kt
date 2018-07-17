package com.capraro.kalidation.constraints.def

import com.capraro.kalidation.constraints.annotation.Values
import org.hibernate.validator.cfg.ConstraintDef

class ValuesDef : ConstraintDef<ValuesDef, Values>(Values::class.java) {

    fun values(values: Array<out String>): ValuesDef {
        addParameter("values", values)
        return this
    }

}