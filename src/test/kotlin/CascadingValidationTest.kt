/*
 * The MIT License
 *
 * Copyright (c) 2018 Richard Capraro
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.capraro.kalidation.constraints.function.negativeOrZero
import com.capraro.kalidation.constraints.function.notBlank
import com.capraro.kalidation.constraints.function.valid
import com.capraro.kalidation.dsl.constraints
import com.capraro.kalidation.dsl.property
import com.capraro.kalidation.dsl.validationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

private class RootTestClass(val field1: String,
                            val field2: LeafTestClass)

private class LeafTestClass(val innerField1: String,
                            val innerField2: Int)

class CascadingValidationTest {

    @Test
    fun `test validation of Cascading class`() {
        val spec = validationSpec {
            constraints<RootTestClass> {
                property(RootTestClass::field1) {
                    notBlank()
                }
                property(RootTestClass::field2) {
                    valid()
                }
            }
            constraints<LeafTestClass> {
                property(LeafTestClass::innerField1) {
                    notBlank()
                }
                property(LeafTestClass::innerField2) {
                    negativeOrZero()
                }
            }
        }
        val dslTest = RootTestClass(field1 = "foobar",
                field2 = LeafTestClass("", 10))

        val validated = spec.validate(dslTest)

        assertThat(validated.isInvalid)

        validated.fold(
                {
                    assertThat(it).extracting("fieldName")
                            .containsExactlyInAnyOrder(
                                    "field2.innerField1",
                                    "field2.innerField2")
                },
                { fail("The validation should not be valid") }
        )
    }
}
