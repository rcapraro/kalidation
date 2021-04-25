import io.github.rcapraro.kalidation.constraints.function.assertTrue
import io.github.rcapraro.kalidation.constraints.function.min
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.constraints.function.range
import io.github.rcapraro.kalidation.constraints.function.valid
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.returnOf
import io.github.rcapraro.kalidation.dsl.validationSpec
import io.github.rcapraro.kalidation.exception.KalidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ClassWithMethods(
    val field1: Int,
    val field2: Int,
    val embeddedClass: EmbeddedClassWithMethod?
) {
    fun validate1(): Boolean {
        return field1 > field2
    }

    fun total(): Int {
        return field1 + field2
    }

    fun methodWithArguments(arg1: Any): Any? {
        return arg1
    }
}

class EmbeddedClassWithMethod {
    fun embeddedValidate() = true
}

class MethodValidationTest {

    @Test
    fun `test validation of class with methods`() {
        val spec = validationSpec {
            constraints<ClassWithMethods> {
                property(ClassWithMethods::field1) {
                    range(0, 5)
                }
                returnOf(ClassWithMethods::validate1, "field1") {
                    notNull()
                    assertTrue()
                }
                returnOf(ClassWithMethods::total) {
                    notNull()
                    min(10)
                }
            }
        }
        val dslTest = ClassWithMethods(1, 3, null)

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder("field1", "total.<return value>")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation by a method embedded in a nested class should fail`() {

        assertThatThrownBy {
            val spec = validationSpec {
                constraints<ClassWithMethods> {
                    property(ClassWithMethods::embeddedClass) {
                        valid()
                    }
                }
                constraints<EmbeddedClassWithMethod> {
                    returnOf(EmbeddedClassWithMethod::embeddedValidate) {
                        assertTrue()
                    }
                }
            }

            val dslTest = ClassWithMethods(1, 3, EmbeddedClassWithMethod())

            spec.validate(dslTest)
        }.isInstanceOf(KalidationException::class.java)
    }

    @Test
    fun `test validation of class with methods should throw an exception if method has arguments`() {

        assertThatThrownBy {
            validationSpec {
                constraints<ClassWithMethods> {

                    returnOf(ClassWithMethods::methodWithArguments) {
                        notNull()
                    }
                }
            }
        }.isInstanceOf(KalidationException::class.java)
    }
}
