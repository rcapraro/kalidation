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
 * Interface for all the [ConstraintRule] implementations and its implementations.
 * @author Richard Capraro
 * @since 0.0.1
 */
sealed class ConstraintRule

//All classes

class NotNull : ConstraintRule()
class Null : ConstraintRule()

//Array

class ArraySize(val min: Int, val max: Int) : ConstraintRule()
class ArrayNotEmpty : ConstraintRule()

//Iterable

class IterableSize(val min: Int, val max: Int) : ConstraintRule()
class IterableNotEmpty : ConstraintRule()

//Boolean

class AssertTrue : ConstraintRule()
class AssertFalse : ConstraintRule()

//CharSequence

class NotBlank : ConstraintRule()
class CsNotEmpty : ConstraintRule()
data class CsSize(val min: Int, val max: Int) : ConstraintRule()
data class Regexp(val regexp: String) : ConstraintRule()
class Email : ConstraintRule()
data class PhoneNumber(val regionCode: String) : ConstraintRule()
data class InValues(val values: List<String>) : ConstraintRule()
class CsMin(val value: Long) : ConstraintRule()
class CsMax(val value: Long) : ConstraintRule()
class CsDecimalMin(val value: String, val inclusive: Boolean) : ConstraintRule()
class CsDecimalMax(val value: String, val inclusive: Boolean) : ConstraintRule()
class CsDigits(val integer: Int, val fraction: Int) : ConstraintRule()

//Number

class NegativeOrZero : ConstraintRule()
class PositiveOrZero : ConstraintRule()
class Negative : ConstraintRule()
class Positive : ConstraintRule()
class Min(val value: Long) : ConstraintRule()
class Max(val value: Long) : ConstraintRule()
class DecimalMin(val value: String, val inclusive: Boolean) : ConstraintRule()
class DecimalMax(val value: String, val inclusive: Boolean) : ConstraintRule()
class Digits(val integer: Int, val fraction: Int) : ConstraintRule()

//Temporal

class Future : ConstraintRule()
class Past : ConstraintRule()
class FutureOrPresent : ConstraintRule()
class PastOrPresent : ConstraintRule()