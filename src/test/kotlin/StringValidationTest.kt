import com.capraro.kalidation.constraints.function.decimalMax
import com.capraro.kalidation.constraints.function.decimalMin
import com.capraro.kalidation.constraints.function.digits
import com.capraro.kalidation.constraints.function.email
import com.capraro.kalidation.constraints.function.inIso8601DateRange
import com.capraro.kalidation.constraints.function.inValues
import com.capraro.kalidation.constraints.function.iso8601Date
import com.capraro.kalidation.constraints.function.max
import com.capraro.kalidation.constraints.function.min
import com.capraro.kalidation.constraints.function.negative
import com.capraro.kalidation.constraints.function.negativeOrZero
import com.capraro.kalidation.constraints.function.notBlank
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.phoneNumber
import com.capraro.kalidation.constraints.function.positive
import com.capraro.kalidation.constraints.function.positiveOrZero
import com.capraro.kalidation.constraints.function.range
import com.capraro.kalidation.constraints.function.regexp
import com.capraro.kalidation.constraints.function.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class StringTestClass(
    val field1: String?,
    val field2: String,
    val field3: String,
    val field4: String? = "0.0"
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
