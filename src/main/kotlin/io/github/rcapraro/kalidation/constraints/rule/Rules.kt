/*
 * The MIT License
 *
 * Copyright (c) 2018 Richard Capraro
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.rcapraro.kalidation.constraints.rule

import io.github.rcapraro.kalidation.constraints.def.CsExactSizeDef
import io.github.rcapraro.kalidation.constraints.def.CsNegativeDef
import io.github.rcapraro.kalidation.constraints.def.CsNegativeOrZeroDef
import io.github.rcapraro.kalidation.constraints.def.CsPositiveDef
import io.github.rcapraro.kalidation.constraints.def.CsPositiveOrZeroDef
import io.github.rcapraro.kalidation.constraints.def.InIso8601DateRangeDef
import io.github.rcapraro.kalidation.constraints.def.Iso8601DateDef
import io.github.rcapraro.kalidation.constraints.def.MapHasKeysDef
import io.github.rcapraro.kalidation.constraints.def.PhoneNumberDef
import io.github.rcapraro.kalidation.constraints.def.SubSetDef
import io.github.rcapraro.kalidation.constraints.def.ValuesDef
import org.hibernate.validator.cfg.ConstraintDef
import org.hibernate.validator.cfg.defs.AssertFalseDef
import org.hibernate.validator.cfg.defs.AssertTrueDef
import org.hibernate.validator.cfg.defs.DecimalMaxDef
import org.hibernate.validator.cfg.defs.DecimalMinDef
import org.hibernate.validator.cfg.defs.DigitsDef
import org.hibernate.validator.cfg.defs.EmailDef
import org.hibernate.validator.cfg.defs.FutureDef
import org.hibernate.validator.cfg.defs.FutureOrPresentDef
import org.hibernate.validator.cfg.defs.MaxDef
import org.hibernate.validator.cfg.defs.MinDef
import org.hibernate.validator.cfg.defs.NegativeDef
import org.hibernate.validator.cfg.defs.NegativeOrZeroDef
import org.hibernate.validator.cfg.defs.NotBlankDef
import org.hibernate.validator.cfg.defs.NotEmptyDef
import org.hibernate.validator.cfg.defs.NotNullDef
import org.hibernate.validator.cfg.defs.NullDef
import org.hibernate.validator.cfg.defs.PastDef
import org.hibernate.validator.cfg.defs.PastOrPresentDef
import org.hibernate.validator.cfg.defs.PatternDef
import org.hibernate.validator.cfg.defs.PositiveDef
import org.hibernate.validator.cfg.defs.PositiveOrZeroDef
import org.hibernate.validator.cfg.defs.RangeDef
import org.hibernate.validator.cfg.defs.ScriptAssertDef
import org.hibernate.validator.cfg.defs.SizeDef

/**
 * Interface for all the [ConstraintRule] implementations and their implementations.
 * @author Richard Capraro
 * @since 0.0.1
 */
sealed class ConstraintRule(open val message: String?, val constraintDef: ConstraintDef<*, *>) {
    fun translate(): ConstraintDef<*, *> = constraintDef.customMessage(message)
}

fun ConstraintDef<*, *>.customMessage(message: String?): ConstraintDef<*, *> {
    message?.let {
        this.message(message)
    }
    return this
}

// All classes

class NotNull(override val message: String?) : ConstraintRule(message, NotNullDef())

class Null(override val message: String?) : ConstraintRule(message, NullDef())
class Valid(override val message: String?) : ConstraintRule(message, NotNullDef())
class ValidByScript(
    lang: String,
    script: String,
    alias: String,
    reportOn: String,
    override val message: String?
) : ConstraintRule(
    message,
    ScriptAssertDef()
        .lang(lang)
        .script(script)
        .alias(alias)
        .reportOn(reportOn)
        .customMessage(message)
)

// Array

class ArraySize(min: Int, max: Int, override val message: String?) :
    ConstraintRule(message, SizeDef().min(min).max(max))

class ArrayNotEmpty(override val message: String?) : ConstraintRule(message, NotEmptyDef())

// Collection

class ColSize(min: Int, max: Int, override val message: String?) :
    ConstraintRule(message, SizeDef().min(min).max(max))

class ColNotEmpty(override val message: String?) : ConstraintRule(message, NotEmptyDef())
class SubSetOf(values: List<String>, override val message: String?) :
    ConstraintRule(message, SubSetDef().completeValues(values.toTypedArray()))

// Map

class MapSize(min: Int, max: Int, override val message: String?) :
    ConstraintRule(message, SizeDef().min(min).max(max))

class MapNotEmpty(override val message: String?) : ConstraintRule(message, NotEmptyDef())
class MapHasKeys(keys: List<String>, override val message: String?) :
    ConstraintRule(message, MapHasKeysDef().mapKeys(keys.toTypedArray()))

// Boolean

class AssertTrue(override val message: String?) : ConstraintRule(message, AssertTrueDef())
class AssertFalse(override val message: String?) : ConstraintRule(message, AssertFalseDef())

// CharSequence

class NotBlank(override val message: String?) : ConstraintRule(message, NotBlankDef())
class CsNotEmpty(override val message: String?) : ConstraintRule(message, NotEmptyDef())
class CsSize(min: Int, max: Int, override val message: String?) :
    ConstraintRule(message, SizeDef().min(min).max(max))
class CsExactSize(size: Int, override val message: String?) :
    ConstraintRule(message, CsExactSizeDef().size(size))

class Regexp(regexp: String, override val message: String?) :
    ConstraintRule(message, PatternDef().regexp(regexp))

class Email(override val message: String?) : ConstraintRule(message, EmailDef())
class PhoneNumber(regionCode: String, override val message: String?) :
    ConstraintRule(message, PhoneNumberDef().regionCode(regionCode))

class Iso8601Date(override val message: String?) : ConstraintRule(message, Iso8601DateDef())
class InIso8601DateRange(startDate: String, stopDate: String, override val message: String?) :
    ConstraintRule(message, InIso8601DateRangeDef().startDate(startDate).stopDate(stopDate))

class InValues(values: List<String>, override val message: String?) :
    ConstraintRule(message, ValuesDef().values(values.toTypedArray()))

class CsPositive(override val message: String?) : ConstraintRule(message, CsPositiveDef())
class CsPositiveOrZero(override val message: String?) : ConstraintRule(message, CsPositiveOrZeroDef())
class CsNegative(override val message: String?) : ConstraintRule(message, CsNegativeDef())
class CsNegativeOrZero(override val message: String?) : ConstraintRule(message, CsNegativeOrZeroDef())
class CsRange(min: Long, max: Long, override val message: String?) :
    ConstraintRule(message, RangeDef().min(min).max(max))

class CsMin(value: Long, override val message: String?) : ConstraintRule(message, MinDef().value(value))
class CsMax(value: Long, override val message: String?) : ConstraintRule(message, MaxDef().value(value))
class CsDecimalMin(value: String, inclusive: Boolean, override val message: String?) :
    ConstraintRule(message, DecimalMinDef().value(value).inclusive(inclusive))

class CsDecimalMax(value: String, inclusive: Boolean, override val message: String?) :
    ConstraintRule(message, DecimalMaxDef().value(value).inclusive(inclusive))

class CsDigits(integer: Int, fraction: Int, override val message: String?) :
    ConstraintRule(message, DigitsDef().integer(integer).fraction(fraction))

// Number

class Range(min: Long, max: Long, override val message: String?) :
    ConstraintRule(message, RangeDef().min(min).max(max))

class NegativeOrZero(override val message: String?) : ConstraintRule(message, NegativeOrZeroDef())
class PositiveOrZero(override val message: String?) : ConstraintRule(message, PositiveOrZeroDef())
class Negative(override val message: String?) : ConstraintRule(message, NegativeDef())
class Positive(override val message: String?) : ConstraintRule(message, PositiveDef())
class Min(value: Long, override val message: String?) : ConstraintRule(message, MinDef().value(value))
class Max(value: Long, override val message: String?) : ConstraintRule(message, MaxDef().value(value))
class DecimalMin(value: String, inclusive: Boolean, override val message: String?) :
    ConstraintRule(message, DecimalMinDef().value(value).inclusive(inclusive))

class DecimalMax(value: String, inclusive: Boolean, override val message: String?) :
    ConstraintRule(message, DecimalMaxDef().value(value).inclusive(inclusive))

class Digits(integer: Int, fraction: Int, override val message: String?) :
    ConstraintRule(message, DigitsDef().integer(integer).fraction(fraction))

// Temporal

class Future(override val message: String?) : ConstraintRule(message, FutureDef())
class Past(override val message: String?) : ConstraintRule(message, PastDef())
class FutureOrPresent(override val message: String?) : ConstraintRule(message, FutureOrPresentDef())
class PastOrPresent(override val message: String?) : ConstraintRule(message, PastOrPresentDef())