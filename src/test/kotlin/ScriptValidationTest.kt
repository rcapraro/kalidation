import com.capraro.kalidation.constraints.function.property.notNull
import com.capraro.kalidation.constraints.function.property.validByScript
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class MyClass(val field: MyInnerClass)

data class MyInnerClass(val innerField1: String, val innerField2: String) {
    fun validate(): Boolean = innerField1.length > innerField2.length
}

class ScriptValidationTest {

    @Test
    fun `test validation by javascript script should fail`() {

        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    validByScript("javascript", "it.innerField1.size > it.innerField2.size", "it")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foo", "foobar"))

        val validated = spec.validate(dslTest)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName").containsExactly("field")
                },
                { fail("The validation should not be valid") }
        )

    }

    @Test
    fun `test validation by javascript script should succeed`() {
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

    @Test
    fun `test validation by javascript script calling a function should succeed`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("javascript", "field.validate()", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foobar", "bar"))

        val validated = spec.validate(dslTest)

        assert(validated.isValid)
    }

    @Test
    fun `test validation by jexl script should fail`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("jexl", "field.innerField1.size() > field.innerField2.size()", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foo", "foobar"))

        val validated = spec.validate(dslTest)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName").containsExactly("field")
                },
                { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation by jexl script calling a function should succeed`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("jexl", "field.validate()", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foobar", "bar"))

        val validated = spec.validate(dslTest)

        assert(validated.isValid)
    }

    @Test
    fun `test validation by groovy script should fail`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("groovy", "field.innerField1.size() > field.innerField2.size()", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foo", "foobar"))

        val validated = spec.validate(dslTest)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName").containsExactly("field")
                },
                { fail("The validation should not be valid") }
        )

    }

    @Test
    fun `test validation by groovy script calling a function should succeed`() {
        val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field) {
                    notNull()
                    validByScript("groovy", "field.validate()", "field")
                }
            }
        }

        val dslTest = MyClass(MyInnerClass("foobar", "bar"))

        val validated = spec.validate(dslTest)

        assert(validated.isValid)
    }

}
