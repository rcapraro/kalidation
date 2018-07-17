import com.capraro.kalidation.constraints.decimalMin
import com.capraro.kalidation.constraints.digits
import com.capraro.kalidation.constraints.min
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import com.capraro.kalidation.implementation.HibernateValidatorFactory
import org.junit.jupiter.api.Test
import java.math.BigDecimal

private data class NumberTestClass(val iField: Int, val bdField: BigDecimal)

class NumberValidationTest {

    @Test
    fun `test validation of Number fields`() {
        val spec = validationSpec {
            constraints<NumberTestClass> {
                property(NumberTestClass::iField) {
                    min(5)
                }
                property(NumberTestClass::bdField) {
                    decimalMin("500.55", true)
                    digits(2, 2)
                }
            }
        }
        val validator = HibernateValidatorFactory(spec).build()
        val test = NumberTestClass(3, BigDecimal("400.60"))

        val violations = validator.validate(test)
        println(violations)

    }
}
