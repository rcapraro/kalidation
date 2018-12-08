import com.capraro.kalidation.constraints.function.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class StringTestClass(val field1: String?,
                              val field2: String,
                              val field3: String,
                              val field4: String? = "0.0")

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
        val dslTest = StringTestClass(" ",
                "richard.capraro#mail.com",
                "(378) 400-1234")

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName")
                            .containsExactlyInAnyOrder(
                                    "field1",
                                    "field1",
                                    "field1",
                                    "field2",
                                    "field2",
                                    "field3")
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
        val dslTest = StringTestClass("234",
                "36.85",
                "-73.955")

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        println(validated)

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
                                    "field3")
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

        assert(validated.isValid)
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

        assert(validated.isValid)
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

        assert(validated.isInvalid)

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
}

