import com.capraro.kalidation.constraints.function.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.tuple
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
                    Assertions.assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(
                                    tuple("bdField", "{javax.validation.constraints.DecimalMin.message}"),
                                    tuple("bdField", "{javax.validation.constraints.DecimalMax.message}"),
                                    tuple("bdField", "{javax.validation.constraints.Digits.message}"),
                                    tuple("iField", "{javax.validation.constraints.Min.message}"),
                                    tuple("iField", "{javax.validation.constraints.Max.message}"),
                                    tuple("iField", "{javax.validation.constraints.Negative.message}"),
                                    tuple("iField", "{javax.validation.constraints.NegativeOrZero.message}")
                            )
                },
                { fail("The validation should not be valid") }
        )

    }
}