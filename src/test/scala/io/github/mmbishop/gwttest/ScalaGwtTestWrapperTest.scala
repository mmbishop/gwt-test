/*
 * Copyright 2023 Michael Bishop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mmbishop.gwttest

import io.github.mmbishop.gwttest.ScalaGwtTestWrapperTest.TestContext
import io.github.mmbishop.gwttest.functions.GwtFunction
import io.github.mmbishop.gwttest.model.Context
import org.junit.jupiter.api.Test

object ScalaGwtTestWrapperTest {
  class TestContext extends Context {
    var number1: Int = _
    var number2: Int = _
    var result: Int = _
  }
}

class ScalaGwtTestWrapperTest {

  @Test
  def scala_wrapper_invokes_gwt_test_core(): Unit = {
    gwt.test()
      .Given(a_number)
      .And(another_number)
      .When(multiplying_the_numbers)
      .Then(the_product_is_calculated)
  }

  private val gwt: ScalaGwtTest[TestContext] = new ScalaGwtTest(classOf[TestContext])

  private val a_number: GwtFunction[TestContext] = context => context.number1 = 5

  private val another_number: GwtFunction[TestContext] = context => context.number2 = 3

  private val multiplying_the_numbers: GwtFunction[TestContext] = context => context.result = context.number1 * context.number2

  private val the_product_is_calculated: GwtFunction[TestContext] = context => assert(context.result == 15)

}
