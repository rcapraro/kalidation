import com.capraro.kalidation.constraints.function.method.assertTrue
import com.capraro.kalidation.constraints.function.method.min
import com.capraro.kalidation.constraints.function.method.notNull
import com.capraro.kalidation.constraints.function.property.range
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.returnOf
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ClassWithMethods(val field1: Int,
                       val field2: Int) {
    fun validate1(): Boolean {
        return field1 > field2
    }

    fun total(): Int {
        return field1 + field2
    }
}

class MethodValidationTest {

    @Test
    fun `test validation of class with methods`() {
        val spec = validationSpec {
            constraints<ClassWithMethods> {
                property(ClassWithMethods::field1) {
                    range(0, 5)
                }
                returnOf(ClassWithMethods::validate1) {
                    notNull()
                    assertTrue()
                }
                returnOf(ClassWithMethods::total) {
                    notNull()
                    min(10)
                }
            }
        }
        val dslTest = ClassWithMethods(1, 3)

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName").containsExactlyInAnyOrder("validate1.<return value>", "total.<return value>")
                },
                { fail("The validation should not be valid") }
        )

    }
}
