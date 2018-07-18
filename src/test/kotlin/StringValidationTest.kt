import com.capraro.kalidation.constraints.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class StringAndBooleanTestClass(val stringField: String, val emailField: String, val booleanField: Boolean)

class StringValidationTest {

    @Test
    fun `test validation of String fields`() {
        val spec = validationSpec {
            constraints<StringAndBooleanTestClass> {
                property(StringAndBooleanTestClass::stringField) {
                    notBlank()
                    notEmpty()
                    notNull()
                    inValues("GREEN", "WHITE", "RED")
                    size(3, 5)
                }
                property(StringAndBooleanTestClass::emailField) {
                    email()
                    regexp("[A-Za-z0-9]+")
                    regexp("[a-z#\\.]*")
                }
            }
        }
        val dslTest = StringAndBooleanTestClass("", "richard.capraro#mail.com", true)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(tuple("stringField", "{javax.validation.constraints.NotBlank.message}"),
                                    tuple("stringField", "{javax.validation.constraints.NotEmpty.message}"),
                                    tuple("stringField", "{javax.validation.constraints.Values.message}"),
                                    tuple("stringField", "{javax.validation.constraints.Size.message}"),
                                    tuple("emailField", "{javax.validation.constraints.Email.message}"),
                                    tuple("emailField", "{javax.validation.constraints.Pattern.message}"))
                },
                { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Boolean fields`() {
        val spec = validationSpec {
            constraints<StringAndBooleanTestClass> {
                property(StringAndBooleanTestClass::booleanField) {
                    assertFalse()
                }
            }
        }
        val dslTest = StringAndBooleanTestClass("", "", true)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("propertyPath.currentLeafNode.name", "constraintDescriptor.annotationDescriptor.type.name")
                            .containsExactlyInAnyOrder(tuple("booleanField", "javax.validation.constraints.AssertFalse"))
                },
                { fail("The validation should not be valid") }
        )
    }
}
