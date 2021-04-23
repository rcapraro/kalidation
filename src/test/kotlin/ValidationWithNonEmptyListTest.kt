import com.capraro.kalidation.constraints.function.notEmpty
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail


class ValidationWithNonEmptyListTest {

    private class ArrayClass(val arrayField: Array<String>)

    @Test
    fun `test validation of Array fields with NonEmptyList as error container`() {
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

        val validated = spec.validateNel(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName").containsExactly("arrayField")
            },
            { fail("The validation should not be valid") }
        )
    }
}
