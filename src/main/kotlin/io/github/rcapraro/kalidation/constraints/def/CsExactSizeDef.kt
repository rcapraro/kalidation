package io.github.rcapraro.kalidation.constraints.def

import io.github.rcapraro.kalidation.constraints.annotation.CsExactSize
import org.hibernate.validator.cfg.ConstraintDef

/**
 * CharSequence exact size validation definition.
 * @author Richard Capraro
 * @since 1.9.0
 */
class CsExactSizeDef : ConstraintDef<CsExactSizeDef, CsExactSize>(CsExactSize::class.java) {

    fun size(size: Int): CsExactSizeDef {
        addParameter("size", size)
        return this
    }
}