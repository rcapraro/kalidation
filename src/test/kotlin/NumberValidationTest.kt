import com.capraro.kalidation.constraints.function.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
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

        assert(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName")
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