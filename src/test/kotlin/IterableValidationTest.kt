import com.capraro.kalidation.constraints.function.notEmpty
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class IterableClass(val listField: List<String>)

class IterableValidationTest {

    @Test
    fun `test validation of Iterable fields`() {
        val spec = validationSpec {
            constraints<IterableClass> {
                property(IterableClass::listField) {
                    notNull()
                    size(3, 5)
                    notEmpty()
                }
            }
        }
        val dslTest = IterableClass(listOf("one", "two"))

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName")
                            .containsExactly("listField")
                },
                { fail("The validation should not be valid") }
        )

    }
}
