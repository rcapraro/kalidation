import io.github.rcapraro.kalidation.constraints.function.decimalMax
import io.github.rcapraro.kalidation.constraints.function.decimalMin
import io.github.rcapraro.kalidation.constraints.function.digits
import io.github.rcapraro.kalidation.constraints.function.max
import io.github.rcapraro.kalidation.constraints.function.min
import io.github.rcapraro.kalidation.constraints.function.negative
import io.github.rcapraro.kalidation.constraints.function.negativeOrZero
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.constraints.function.positive
import io.github.rcapraro.kalidation.constraints.function.positiveOrZero
import io.github.rcapraro.kalidation.constraints.function.range
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.math.BigDecimal

private data class NumberTestClass(val iField: Int, val bdField: BigDecimal)

class NumberValidationTest {

    @Test
    fun `test validation of Number fields`() {
        val spec = validationSpec {
            constraints<NumberTestClass> {
                property(NumberTestClass::iField) {
                    notNull()
                    min(5)
                    max(2)
                    positive()
                    positiveOrZero()
                    negative()
                    negativeOrZero()
                    range(5, 8)
                }
                property(NumberTestClass::bdField) {
                    decimalMin("500.55", true)
                    decimalMax("260.80", false)
                    digits(2, 2)
                }
            }
        }

        val dslTest = NumberTestClass(3, BigDecimal("400.60"))
        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder(
                        "bdField",
                        "bdField",
                        "bdField",
                        "iField",
                        "iField",
                        "iField",
                        "iField",
                        "iField"
                    )
            },
            { fail("The validation should not be valid") }
        )
    }
}
