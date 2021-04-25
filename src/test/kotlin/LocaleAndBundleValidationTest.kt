import io.github.rcapraro.kalidation.constraints.function.inValues
import io.github.rcapraro.kalidation.constraints.function.notBlank
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.Locale

private class ValidationTestClass(val field: String)

class LocaleAndBundleValidationTest {

    @Test
    fun `test validation with custom resourceBundle and EN Locale`() {
        val spec = validationSpec(messageBundle = "TestMessages", locale = Locale.ENGLISH) {
            constraints<ValidationTestClass> {
                property(ValidationTestClass::field) {
                    notBlank()
                    inValues("FOO", "BAR")
                }
            }
        }

        val dslTest = ValidationTestClass(" ")

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName", "message")
                    .containsExactlyInAnyOrder(
                        tuple("field", "[EN] is blank"),
                        tuple("field", "[EN] not in values [FOO, BAR]")
                    )
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation with custom resourceBundle and FR Locale`() {
        val spec = validationSpec(messageBundle = "TestMessages", locale = Locale.FRENCH) {
            constraints<ValidationTestClass> {
                property(ValidationTestClass::field) {
                    notBlank()
                    inValues("FOO", "BAR")
                }
            }
        }

        val dslTest = ValidationTestClass(" ")

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName", "message")
                    .containsExactlyInAnyOrder(
                        tuple("field", "[FR] est blanc"),
                        tuple("field", "[FR] pas dans les valeurs [FOO, BAR]")
                    )
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation with custom message`() {
        val spec = validationSpec {
            constraints<ValidationTestClass> {
                property(ValidationTestClass::field) {
                    notBlank(message = "is.blank")
                }
            }
        }

        val dslTest = ValidationTestClass(" ")

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName", "message")
                    .containsExactly(tuple("field", "is.blank"))
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation with custom message key`() {
        val spec = validationSpec(messageBundle = "TestMessages", locale = Locale.FRENCH) {
            constraints<ValidationTestClass> {
                property(ValidationTestClass::field) {
                    notBlank(message = "{field.is.blank.message}")
                }
            }
        }

        val dslTest = ValidationTestClass(" ")

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName", "message")
                    .containsExactly(tuple("field", "[FR] field is blank custom"))
            },
            { fail("The validation should not be valid") }
        )
    }
}
