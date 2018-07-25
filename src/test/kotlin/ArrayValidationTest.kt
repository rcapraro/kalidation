import com.capraro.kalidation.constraints.function.notEmpty
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.tuple
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

        assert(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactly(tuple("arrayField", "{javax.validation.constraints.Size.message}"))
                },
                { fail("The validation should not be valid") }
        )

    }
}
