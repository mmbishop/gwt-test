### gwt-test example - Scala

There are several significant differences in the use of gwt-test in Scala compared to Java and Groovy:

1. The context class is declared inside of an ```object``` at the top of the test class, and the test implementation is contained in the companion class.
2. Fields in the context class must be ```var```s and must be explicitly initialized. You can use the default value constant ```_``` for this purpose, as in
   ```var field = _```.
3. In Scala, ```given``` and ```then``` are reserved words and cannot be used as method names. To call those methods in a Scala test, enclose the method names
   in backquotes: `` ` ``given`` ` `` and `` ` ``then`` ` ``. You can also enclose the method names ```when``` and ```and``` in backquotes for the sake
   of consistency.
4. When instantiating the test object, the type argument is specified as ```classOf[class-name]``` so that the Java framework can instantiate it.
5. Test methods return ```Unit```, which is the Scala equivalent of the Java ```void``` type.

Here's an example of a gwt-test written in Scala:

```scala
package gwttest.example

import gwttest.example.DivisionTest.TestContext
import io.github.mmbishop.gwttest.core.GwtTest
import io.github.mmbishop.gwttest.functions.GwtFunction
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument
import io.github.mmbishop.gwttest.model.Context
import org.junit.jupiter.api.Test
import org.hamcrest.CoreMatchers.is
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertTrue

object DivisionTest {
  class TestContext extends Context {
    var dividend: Double = _
    var divisor: Double = _
    var quotient: Double = _
  }
}

class DivisionTest {

  @Test
  def quotient_of_two_numbers_is_calculated(): Unit = {
    gwt.test()
      .`given`(a_dividend, 12.0)
      .and(a_divisor, 4.0)
      .when(dividing_the_dividend_by_the_divisor)
      .`then`(the_quotient_is, 3.0)
  }

  @Test
  def dividing_a_number_by_zero_results_in_infinity(): Unit = {
    gwt.test()
      .`given`(a_dividend, 6.0)
      .when(dividing_the_dividend_by_zero)
      .`then`(the_quotient_is_infinity)
  }

  private val gwt: GwtTest[TestContext] = new GwtTest[TestContext](classOf[TestContext])

  private val a_dividend: GwtFunctionWithArgument[TestContext, Double]
      = (context, dividend) => context.dividend = dividend

  private val a_divisor: GwtFunctionWithArgument[TestContext, Double]
      = (context, divisor) => context.divisor = divisor

  private val dividing_the_dividend_by_the_divisor: GwtFunction[TestContext]
      = context => context.quotient = context.dividend / context.divisor

  private val dividing_the_dividend_by_zero: GwtFunction[TestContext]
      = context => context.quotient = context.dividend / 0.0

  private val the_quotient_is: GwtFunctionWithArgument[TestContext, Double]
      = (context, expectedQuotient) => assertThat(context.quotient, is(expectedQuotient))

  private val the_quotient_is_infinity: GwtFunction[TestContext]
      = context => assertTrue(context.quotient.isPosInfinity)

}
```