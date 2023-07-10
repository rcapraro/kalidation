import io.github.rcapraro.kalidation.constraints.function.hasKeys
import io.github.rcapraro.kalidation.constraints.function.notEmpty
import io.github.rcapraro.kalidation.constraints.function.notNull
import io.github.rcapraro.kalidation.constraints.function.size
import io.github.rcapraro.kalidation.dsl.constraints
import io.github.rcapraro.kalidation.dsl.property
import io.github.rcapraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class MapClass(val mapField: Map<String, Int>)

class MapValidationTest {

    @Test
    fun `test validation of Map field`() {
        val spec = validationSpec {
            constraints<MapClass> {
                property(MapClass::mapField) {
                    notNull()
                    notEmpty()
                    size(3)
                    hasKeys("one", "two")
                    hasKeys("three", "four")
                }
            }
        }
        val dslTest = MapClass(mapOf("one" to 1, "two" to 2))

        val validated = spec.validate(dslTest)

        assertThat(validated.isLeft())

        validated.fold(
            {
                assertThat(it).extracting("fieldName")
                    .containsExactlyInAnyOrder("mapField", "mapField")
            },
            { fail("The validation should not be valid") }
        )
    }
}