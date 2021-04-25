import io.github.rcapraro.kalidation.constraints.function.notEmpty
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.constraints.function.size
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class ArrayClass(val arrayField: Array<String>)

class ArrayValidationTest {

    @Test
    fun `test validation of Array fields`() {
        val spec = validationSpec {
            constraints<ArrayClass> {
                property(ArrayClass::arrayField) {
                    notNull()
                    size(3, 5)
                    notEmpty()
                }
            }
        }
        val dslTest = ArrayClass(arrayOf("one", "two"))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName").containsExactly("arrayField")
            },
            { fail("The validation should not be valid") }
        )
    }
}
