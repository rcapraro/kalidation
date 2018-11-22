package com.capraro.kalidation.implementation

import java.util.*
import javax.validation.MessageInterpolator

/**
 * [MessageInterpolator] which handles the [Locale]
 * @author rcapraro
 * Date: 22/11/2018
 */
data class LocaleSpecificMessageInterpolator(val interpolator: MessageInterpolator, val locale: Locale) : MessageInterpolator {
    
    override fun interpolate(message: String?, context: MessageInterpolator.Context?): String {
        return interpolator.interpolate(message, context, locale)
    }

    override fun interpolate(message: String?, context: MessageInterpolator.Context?, locale: Locale?): String {
        return interpolator.interpolate(message, context, locale)
    }
}