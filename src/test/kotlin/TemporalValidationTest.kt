import io.github.rcapraro.kalidation.constraints.function.future
import io.github.rcapraro.kalidation.constraints.function.futureOrPresent
import io.github.rcapraro.kalidation.constraints.function.past
import io.github.rcapraro.kalidation.constraints.function.pastOrPresent
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDateTime
import java.time.ZonedDateTime

private data class TemporalTestClass(val ldField: LocalDateTime, val zdField: ZonedDateTime)

class TemporalValidationTest {

    @Test
    fun `test validation of Temporal fields`() {
        val spec = validationSpec {
            constraints<TemporalTestClass> {
                property(TemporalTestClass::ldField) {
                    past()
                    pastOrPresent()
                }
                property(TemporalTestClass::zdField) {
                    future()
                    futureOrPresent()
                }
            }
        }
        val dslTest = TemporalTestClass(
            LocalDateTime.now().plusDays(1),
            ZonedDateTime.now().minusDays(1)
        )

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder(
                        "zdField",
                        "zdField",
                        "ldField",
                        "ldField"
                    )
            },
            { fail("The validation should not be valid") }
        )
    }
}
