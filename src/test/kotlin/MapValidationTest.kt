import com.capraro.kalidation.constraints.function.property.hasKeys
import com.capraro.kalidation.constraints.function.property.notEmpty
import com.capraro.kalidation.constraints.function.property.notNull
import com.capraro.kalidation.constraints.function.property.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions
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

        assertThat(validated.isInvalid)

        validated.fold(
                {
                    Assertions.assertThat(it).extracting("fieldName")
                            .containsExactlyInAnyOrder("mapField", "mapField")
                },
                { fail("The validation should not be valid") }
        )

    }
}
