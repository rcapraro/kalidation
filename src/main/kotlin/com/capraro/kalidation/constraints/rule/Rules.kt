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