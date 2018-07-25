import com.capraro.kalidation.constraints.function.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class StringTestClass(val field1: String,
                              val field2: String,
                              val field3: String)

class StringValidationTest {

    @Test
    fun `test validation of String fields`() {
        val spec = validationSpec {
            constraints<StringTestClass> {
                property(StringTestClass::field1) {
                    notBlank()
                    notEmpty()
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
        val dslTest = StringTestClass("",
                "richard.capraro#mail.com",
                "(378) 400-1234")

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(
                                    tuple("field1", "{javax.validation.constraints.NotBlank.message}"),
                                    tuple("field1", "{javax.validation.constraints.NotEmpty.message}"),
                                    tuple("field1", "{javax.validation.constraints.Values.message}"),
                                    tuple("field1", "{javax.validation.constraints.Size.message}"),
                                    tuple("field2", "{javax.validation.constraints.Email.message}"),
                                    tuple("field2", "{javax.validation.constraints.Pattern.message}"),
                                    tuple("field3", "{javax.validation.constraints.PhoneNumber.message}"))
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
                }
                property(StringTestClass::field2) {
                    decimalMin("45.5", true)
                    decimalMax("23.6", true)
                }
                property(StringTestClass::field3) {
                    digits(3, 2)
                }
            }

        }
        val dslTest = StringTestClass("234",
                "36.85",
                "-73.955")

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(
                                    tuple("field1", "{javax.validation.constraints.Min.message}"),
                                    tuple("field1", "{javax.validation.constraints.Max.message}"),
                                    tuple("field2", "{javax.validation.constraints.DecimalMin.message}"),
                                    tuple("field2", "{javax.validation.constraints.DecimalMax.message}"),
                                    tuple("field3", "{javax.validation.constraints.Digits.message}"))
                },
                { fail("The validation should not be valid") }
        )
    }
}
