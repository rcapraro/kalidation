import arrow.core.NonEmptyList
import io.github.rcapraro.kalidation.constraints.function.email
import io.github.rcapraro.kalidation.constraints.function.notEmpty
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.eachElement
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test

private class ContainerListClass(val listField: List<String?>)

private class ContainerMapOfListClass(val mapOfListField: Map<String, List<String?>>)

class ContainerValidationTest {

    @Test
    fun `test validation of Container with List field`() {
        val spec = validationSpec {
            constraints<ContainerListClass> {
                property(ContainerListClass::listField) {
                    notEmpty()
                    eachElement {
                        notNull()
                        email()
                    }
                }
            }
        }

        val dslTest = ContainerListClass(listOf("user@domain.com", "somebody", null))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactly("listField.<list element>", "listField.<list element>")
            },
            { fail("The validation should not be valid") }
        )
    }

    @Test
    fun `test validation of Container with Map of List field`() {
        val spec = validationSpec {
            constraints<ContainerMapOfListClass> {
                property(ContainerMapOfListClass::mapOfListField) {
                    notNull()
                    eachElement(String::class, NonEmptyList(1, listOf(0))) {
                        notNull()
                        email()
                    }
                }
            }
        }

        val dslTest = ContainerMapOfListClass(mapOf("emails" to listOf("user@domain.com", "somebody", null)))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactly(
                        "mapOfListField.<map value>.<list element>",
                        "mapOfListField.<map value>.<list element>"
                    )
            },
            { fail("The validation should not be valid") }
        )
    }
}
