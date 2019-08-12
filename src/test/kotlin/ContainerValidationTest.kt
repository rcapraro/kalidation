import arrow.data.NonEmptyList
import com.capraro.kalidation.constraints.function.email
import com.capraro.kalidation.constraints.function.notNull
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.eachProperty
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class ContainerClass(val listField: List<String?>)

class ContainerValidationTest {

    @Test
    fun `test validation of Container field`() {
        val spec = validationSpec {
            constraints<ContainerClass> {
                eachProperty(ContainerClass::listField, String::class, NonEmptyList.of(0)) {
                    notNull()
                    email()
                }
            }
        }

        val dslTest = ContainerClass(listOf("user@domain.com", "somebody", null))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName")
                            .containsExactly("listField.<list element>", "listField.<list element>")
                },
                { fail("The validation should not be valid") }
        )

    }
}