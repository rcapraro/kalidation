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

package com.capraro.kalidation.constraints.rule

/**
 * Interface for all the [ConstraintRule] implementations and their implementations.
 * @author Richard Capraro
 * @since 0.0.1
 */
sealed class ConstraintRule(var customMessage: String?)

//All classes

class NotNull(val message: String?) : ConstraintRule(message)
class Null(val message: String?) : ConstraintRule(message)
class Valid(val message: String?) : ConstraintRule(message)
class ValidByScript(val lang: String, val script: String, val alias: String, val reportOn: String, val message: String?) : ConstraintRule(message)

//Array

class ArraySize(val min: Int, val max: Int, val message: String?) : ConstraintRule(message)
class ArrayNotEmpty(val message: String?) : ConstraintRule(message)

//Collection

class ColSize(val min: Int, val max: Int, val message: String?) : ConstraintRule(message)
class ColNotEmpty(val message: String?) : ConstraintRule(message)
class SubSetOf(val values: List<String>, val message: String?) : ConstraintRule(message)

//Map

class MapSize(val min: Int, val max: Int, val message: String?) : ConstraintRule(message)
class MapNotEmpty(val message: String?) : ConstraintRule(message)
class MapHasKeys(val keys: List<String>, val message: String?) : ConstraintRule(message)

//Boolean

class AssertTrue(val message: String?) : ConstraintRule(message)
class AssertFalse(val message: String?) : ConstraintRule(message)

//CharSequence

class NotBlank(val message: String?) : ConstraintRule(message)
class CsNotEmpty(val message: String?) : ConstraintRule(message)
class CsSize(val min: Int, val max: Int, val message: String?) : ConstraintRule(message)
class Regexp(val regexp: String, val message: String?) : ConstraintRule(message)
class Email(val message: String?) : ConstraintRule(message)
class PhoneNumber(val regionCode: String, val message: String?) : ConstraintRule(message)
class Iso8601Date(val message: String?) : ConstraintRule(message)
class InValues(val values: List<String>, val message: String?) : ConstraintRule(message)
class CsPositive(val message: String?) : ConstraintRule(message)
class CsPositiveOrZero(val message: String?) : ConstraintRule(message)
class CsNegative(val message: String?) : ConstraintRule(message)
class CsNegativeOrZero(val message: String?) : ConstraintRule(message)
class CsRange(val min: Long, val max: Long, val message: String?) : ConstraintRule(message)
class CsMin(val value: Long, val message: String?) : ConstraintRule(message)
class CsMax(val value: Long, val message: String?) : ConstraintRule(message)
class CsDecimalMin(val value: String, val inclusive: Boolean, val message: String?) : ConstraintRule(message)
class CsDecimalMax(val value: String, val inclusive: Boolean, val message: String?) : ConstraintRule(message)
class CsDigits(val integer: Int, val fraction: Int, val message: String?) : ConstraintRule(message)

//Number

class Range(val min: Long, val max: Long, val message: String?) : ConstraintRule(message)
class NegativeOrZero(val message: String?) : ConstraintRule(message)
class PositiveOrZero(val message: String?) : ConstraintRule(message)
class Negative(val message: String?) : ConstraintRule(message)
class Positive(val message: String?) : ConstraintRule(message)
class Min(val value: Long, val message: String?) : ConstraintRule(message)
class Max(val value: Long, val message: String?) : ConstraintRule(message)
class DecimalMin(val value: String, val inclusive: Boolean, val message: String?) : ConstraintRule(message)
class DecimalMax(val value: String, val inclusive: Boolean, val message: String?) : ConstraintRule(message)
class Digits(val integer: Int, val fraction: Int, val message: String?) : ConstraintRule(message)

//Temporal

class Future(val message: String?) : ConstraintRule(message)
class Past(val message: String?) : ConstraintRule(message)
class FutureOrPresent(val message: String?) : ConstraintRule(message)
class PastOrPresent(val message: String?) : ConstraintRule(message)