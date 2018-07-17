import com.capraro.kalidation.constraints.future
import com.capraro.kalidation.constraints.futureOrPresent
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import com.capraro.kalidation.implementation.HibernateValidatorFactory
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZonedDateTime

private data class TemporalTestClass(val ldField: LocalDateTime, val zdField: ZonedDateTime)

class TemporalValidationTest {

    @Test
    fun `test validation of Temporal fields`() {
        val spec = validationSpec {
            constraints<TemporalTestClass> {
                property(TemporalTestClass::ldField) {
                    future()
                }
                property(TemporalTestClass::zdField) {
                    futureOrPresent()
                }
            }
        }
        val validator = HibernateValidatorFactory(spec).build()
        val test = TemporalTestClass(LocalDateTime.parse("2017-12-03T10:15:30"),
                ZonedDateTime.parse("2017-12-03T10:15:30+01:00[Europe/Paris]"))

        val violations = validator.validate(test)
        println(violations)

    }
}
