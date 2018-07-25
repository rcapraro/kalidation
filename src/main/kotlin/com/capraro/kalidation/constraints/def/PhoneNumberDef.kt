package com.capraro.kalidation.constraints.def

import com.capraro.kalidation.constraints.annotation.PhoneNumber
import org.hibernate.validator.cfg.ConstraintDef

/**
 * PhoneNumber validation definition.
 * @author Richard Capraro
 * @since 0.0.1
 */
class PhoneNumberDef : ConstraintDef<PhoneNumberDef, PhoneNumber>(PhoneNumber::class.java) {

    fun regionCode(regionCode: String): PhoneNumberDef {
        addParameter("regionCode", regionCode)
        return this
    }

}