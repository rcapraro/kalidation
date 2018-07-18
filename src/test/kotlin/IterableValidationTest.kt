import com.capraro.kalidation.constraints.notNull
import com.capraro.kalidation.constraints.size
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.junit.jupiter.api.Test

private class IterableClass(val listField: List<String>)

class IterableValidationTest {

    @Test
    fun `test validation of Iterable fields`() {
        val spec = validationSpec {
            constraints<IterableClass> {
                property(IterableClass::listField) {
                    notNull()
                    size(3, 5)
                }
            }
        }
        val dslTest = IterableClass(listOf("one", "two"))

        val validated = spec.validate(dslTest)

        assert(validated.isInvalid)


    }
}
