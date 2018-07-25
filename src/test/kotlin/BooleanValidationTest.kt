import com.capraro.kalidation.constraints.function.assertFalse
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class BooleanTestClass(val booleanField: Boolean)

class BooleanValidationTest {

    @Test
    fun `test validation of Boolean fields`() {
        val spec = validationSpec {
            constraints<BooleanTestClass> {
                property(BooleanTestClass::booleanField) {
                    assertFalse()
                }
            }
        }
        val dslTest = BooleanTestClass(true)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(tuple("booleanField", "{javax.validation.constraints.AssertFalse.message}"))
                },
                { fail("The validation should not be valid") }
        )
    }
}
