import io.github.rcapraro.kalidation.constraints.function.decimalMax
import io.github.rcapraro.kalidation.constraints.function.decimalMin
import io.github.rcapraro.kalidation.constraints.function.digits
import io.github.rcapraro.kalidation.constraints.function.email
import io.github.rcapraro.kalidation.constraints.function.inIso8601DateRange
import io.github.rcapraro.kalidation.constraints.function.inValues
import io.github.rcapraro.kalidation.constraints.function.iso8601Date
import io.github.rcapraro.kalidation.constraints.function.max
import io.github.rcapraro.kalidation.constraints.function.min
import io.github.rcapraro.kalidation.constraints.function.negative
import io.github.rcapraro.kalidation.constraints.function.negativeOrZero
import io.github.rcapraro.kalidation.constraints.function.notBlank
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.constraints.function.phoneNumber
import io.github.rcapraro.kalidation.constraints.function.positive
import io.github.rcapraro.kalidation.constraints.function.positiveOrZero
import io.github.rcapraro.kalidation.constraints.function.range
import io.github.rcapraro.kalidation.constraints.function.regexp
import io.github.rcapraro.kalidation.constraints.function.size
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class StringTestClass(
    val field1: String?,
    val field2: String,
    val field3: String,
    val field4: String? = "0.0",
    val field5: String = "FOO"
)

class StringValidationTest {

    @Test
    fun `test validation of String fields`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    notBlank()
                    notNull()
                    inValues("GREEN", "WHITE", "RED")
                    size(3, 5)
                }
                property(StringTestClass::field2) {
                    email()
                    regexp("[A-Za-z0-9]+")
                    regexp("[a-z#\\.]*")
                }
                property(StringTestClass::field3) {
                    notBlank()
                    phoneNumber("FR")
                    phoneNumber("US")
                }
                property(StringTestClass::field5) {
                    size(3)
                }
            }
        }
        val dslTest = StringTestClass(
            " ",
            "richard.capraro#mail.com",
            "(378) 400-1234"
        )

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder(
                        "field1",
                        "field1",
                        "field1",
                        "field2",
                        "field2",
                        "field3"
                    )
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of String fields as Numbers`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    min(345)
                    max(123)
                    range(235, 237)
                }
                property(StringTestClass::field2) {
                    decimalMin("45.5", true)
                    decimalMax("23.6", true)
                    negative()
                    negativeOrZero()
                }
                property(StringTestClass::field3) {
                    digits(3, 2)
                    positive()
                    positiveOrZero()
                }
                property(StringTestClass::field4) {
                    notBlank()
                    positiveOrZero()
                    negativeOrZero()
                }
            }
        }
        val dslTest = StringTestClass(
            "234",
            "36.85",
            "-73.955"
        )

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder(
                        "field1",
                        "field1",
                        "field1",
                        "field2",
                        "field2",
                        "field2",
                        "field2",
                        "field3",
                        "field3",
                        "field3"
                    )
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of String with ISO8601 date format`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    iso8601Date()
                }
            }
        }

        val dslTest = StringTestClass("2018-06-15T17:32:28.000Z", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isValid).isTrue
    }

    @Test
    fun `test validation of null String with ISO8601 date format`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    iso8601Date()
                }
            }
        }

        val dslTest = StringTestClass(null, "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isValid).isTrue
    }

    @Test
    fun `test validation of empty String with ISO8601 date format`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    iso8601Date()
                }
            }
        }

        val dslTest = StringTestClass("", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid).isTrue

        validated.fold(
            {
                assertThat(it).extracting("fieldName").containsExactly("field1")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of String without ISO8601 date format`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    iso8601Date()
                }
            }
        }

        val dslTest = StringTestClass("NotZonedDateTime", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        validated.fold(
            {
                assertThat(it).extracting("fieldName").containsExactly("field1")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation inIso8601DateRange date is within range`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    inIso8601DateRange("2018-06-14T17:32:28.000Z", "2018-06-16T17:32:28.000Z")
                }
            }
        }

        val dslTest = StringTestClass("2018-06-14T17:32:28.001Z", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isValid).isTrue
    }

    @Test
    fun `test validation inIso8601DateRange date is equal to starDate`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    inIso8601DateRange("2018-06-14T17:32:28.000Z", "2018-06-16T17:32:28.000Z")
                }
            }
        }

        val dslTest = StringTestClass("2018-06-14T17:32:28.000Z", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isValid).isTrue
    }

    @Test
    fun `test validation inIso8601DateRange date is equal to stopDate`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    inIso8601DateRange("2018-06-14T17:32:28.000Z", "2018-06-16T17:32:28.000Z")
                }
            }
        }

        val dslTest = StringTestClass("2018-06-16T17:32:28.000Z", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        assertThat(validated.isValid).isTrue
    }

    @Test
    fun `test validation out of inIso8601DateRange`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    inIso8601DateRange("2018-06-14T17:32:28.000Z", "2018-06-16T17:32:28.000Z")
                }
            }
        }

        val dslTest = StringTestClass("2018-06-14T17:32:27.001Z", "EmptyNotUsedForTest", "EmptyNotUsedForTest")
        val validated = spec.validate(dslTest)

        validated.fold(
            {
                assertThat(it).extracting("fieldName").containsExactly("field1")
            },
            { fail("The validation should not be valid") }
        )
    }
}