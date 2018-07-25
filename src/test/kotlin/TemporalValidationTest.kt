import com.capraro.kalidation.constraints.function.future
import com.capraro.kalidation.constraints.function.futureOrPresent
import com.capraro.kalidation.constraints.function.past
import com.capraro.kalidation.constraints.function.pastOrPresent
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
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

        assert(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName", "messageTemplate")
                            .containsExactlyInAnyOrder(
                                    Assertions.tuple("zdField", "{javax.validation.constraints.Future.message}"),
                                    Assertions.tuple("zdField", "{javax.validation.constraints.FutureOrPresent.message}"),
                                    Assertions.tuple("ldField", "{javax.validation.constraints.Past.message}"),
                                    Assertions.tuple("ldField", "{javax.validation.constraints.PastOrPresent.message}"))
                },
                { fail("The validation should not be valid") }
        )

    }
}
