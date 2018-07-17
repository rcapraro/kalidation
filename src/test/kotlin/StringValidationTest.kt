import com.capraro.kalidation.constraints.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class StringAndBooleanTestClass(val sField: String, val iField: Int, val bField: Boolean)

class StringValidationTest {

    @Test
    fun `test validation of String fields`() {
        val spec = validationSpec {
            constraints<StringAndBooleanTestClass> {
                property(StringAndBooleanTestClass::sField) {
                    notBlank()
                    notEmpty()
                    notNull()
                    inValues("toto", "titi")
                    size(3, 5)
                }
            }
        }
        val dslTest = StringAndBooleanTestClass("", 3, true)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("propertyPath.currentLeafNode.name", "constraintDescriptor.annotationDescriptor.type.name")
                            .containsOnly(tuple("sField", "javax.validation.constraints.NotEmpty"),
                                    tuple("sField", "javax.validation.constraints.NotBlank"),
                                    tuple("sField", "com.capraro.kalidation.constraints.annotation.Values"),
                                    tuple("sField", "javax.validation.constraints.Size"))
                },
                { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Boolean fields`() {
        val spec = validationSpec {
            constraints<StringAndBooleanTestClass> {
                property(StringAndBooleanTestClass::bField) {
                    assertFalse()
                }
            }
        }
        val dslTest = StringAndBooleanTestClass("", 3, true)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("propertyPath.currentLeafNode.name", "constraintDescriptor.annotationDescriptor.type.name")
                            .containsOnly(tuple("bField", "javax.validation.constraints.AssertFalse"))
                },
                { fail("The validation should not be valid") }
        )
    }
}
