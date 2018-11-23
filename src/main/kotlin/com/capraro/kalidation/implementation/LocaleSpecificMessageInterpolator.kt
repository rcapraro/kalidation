package com.capraro.kalidation.implementation

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator
import java.util.*
import javax.validation.MessageInterpolator

/**
 * [MessageInterpolator] which handles the [Locale]
 * @author rcapraro
 * Date: 22/11/2018
 */
data class LocaleSpecificMessageInterpolator(val locale: Locale,
                                             var resourceBundleLocators: AggregateResourceBundleLocator) : ResourceBundleMessageInterpolator(resourceBundleLocators) {

    override fun interpolate(message: String?, context: MessageInterpolator.Context?): String {
        return super.interpolate(message, context, locale)
    }

    override fun interpolate(message: String?, context: MessageInterpolator.Context?, locale: Locale?): String {
        return super.interpolate(message, context, locale)
    }
}