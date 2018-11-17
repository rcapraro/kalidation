import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.validByScript
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class MyClass(val field: MyInnerClass)

data class MyInnerClass(val innerField1: String, val innerField2: String)

class ScriptValidationTest {

    @Test
    fun `test validation by script should fail`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("javascript", "field.innerField1.size > field.innerField2.size", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foo", "foobar"))

        val validated = spec.validate(dslTest)

        println(validated)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName").containsExactly("field")
                },
                { fail("The validation should not be valid") }
        )

    }

    @Test
    fun `test validation by script should succeed`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("javascript", "field.innerField1 == field.innerField2", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foobar", "foobar"))

        val validated = spec.validate(dslTest)

        assert(validated.isValid)

    }
}
