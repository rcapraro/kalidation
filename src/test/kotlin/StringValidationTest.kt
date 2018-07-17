import com.capraro.kalidation.constraints.*
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import com.capraro.kalidation.implementation.HibernateValidatorFactory
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test

private class TestClass1(val sField: String, val iField: Int, val bField: Boolean)
private class TestClass2(val sField: String, val iField: Int?, val bField: Boolean)

class StringValidationTest {

    @Test
    fun `test validation of String fields`() {
        val spec = validationSpec {
            constraints<TestClass1> {
                property(TestClass1::sField) {
                    notBlank()
                    notEmpty()
                    notNull()
                }
                property(TestClass1::bField) {
                    assertFalse()
                }
            }
            constraints<TestClass2> {
                property(TestClass2::sField) {
                    size(3, 5)
                    notBlank()
                    inValues("toto", "titi")
                }
                property(TestClass2::iField) {
                    notNull()
                }
            }
        }
        val validator = HibernateValidatorFactory(spec).build()
        val dslTest1 = TestClass1("", 3, true)
        val dslTest2 = TestClass2("hello world", null, false)

        val violations1 = validator.validate(dslTest1)
        assert(violations1.isNotEmpty())
        println(violations1)
        assertThat(violations1)
                .extracting("propertyPath.currentLeafNode.name", "constraintDescriptor.annotationDescriptor.type.name")
                .containsOnly(tuple("sField", "javax.validation.constraints.NotEmpty"),
                        tuple("sField", "javax.validation.constraints.NotBlank"),
                        tuple("bField", "javax.validation.constraints.AssertFalse"))

        val violations2 = validator.validate(dslTest2)
        assert(violations2.isNotEmpty())
        println(violations2)
    }
}
