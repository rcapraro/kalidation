import com.capraro.kalidation.constraints.function.notEmpty
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.constraints.function.size
import com.capraro.kalidation.constraints.function.subSetOf
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class CollectionClass(val listField: List<String>)

class CollectionValidationTest {

    @Test
    fun `test validation of Collection field`() {
        val spec = validationSpec {
            constraints<CollectionClass> {
                property(CollectionClass::listField) {
                    notNull()
                    size(3, 5)
                    notEmpty()
                }
            }
        }
        val dslTest = CollectionClass(listOf("one", "two"))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactly("listField")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Collection field is not subset of other list`() {
        val spec = validationSpec {
            constraints<CollectionClass> {
                property(CollectionClass::listField) {
                    subSetOf("GREEN", "WHITE", "RED")
                }
            }
        }
        val dslTest = CollectionClass(listOf("one", "two"))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactly("listField")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Collection field is partial subset of other list`() {
        val spec = validationSpec {
            constraints<CollectionClass> {
                property(CollectionClass::listField) {
                    subSetOf("GREEN", "WHITE", "RED")
                }
            }
        }
        val dslTest = CollectionClass(listOf("GREEN", "two"))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactly("listField")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Collection field is subset of other list`() {
        val spec = validationSpec {
            constraints<CollectionClass> {
                property(CollectionClass::listField) {
                    subSetOf("GREEN", "WHITE", "RED")
                }
            }
        }
        val dslTest = CollectionClass(listOf("GREEN", "WHITE"))

        val validated = spec.validate(dslTest)

        assertThat(validated.isValid)
    }

    @Test
    fun `test validation of empty Collection field is subset of other empty list`() {
        val spec = validationSpec {
            constraints<CollectionClass> {
                property(CollectionClass::listField) {
                    subSetOf()
                }
            }
        }
        val dslTest = CollectionClass(listOf())

        val validated = spec.validate(dslTest)

        assertThat(validated.isValid)
    }
}
