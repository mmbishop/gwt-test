### gwt-test example Kotlin

There are several significant differences in the use of gwt-test in Kotlin compared to Java and Groovy:

1. The context class is declared inside of a ```companion object```.
2. Fields in the context class that are objects (i.e., class instances) must be declared as ```lateinit``` or be explicitly initialized. Fields of primitive 
   types must be explicitly initialized.
3. In Kotlin, ```when``` is a reserved word and cannot be used as a method name. To call that method in a Kotlin test, enclose its name in backquotes:
   `` ` ``when`` ` ``. You can also enclose the method names ```given```, ```then``` and ```and``` in backquotes for the sake of consistency, though
   the Kotlin compiler may give you a warning if you do so.
4. When instantiating the test object, the type argument is specified as a Java type as follows: ```<context-class-name>::class.javaObjectType```.

Here's an example of a gwt-test written in Kotlin:

```kotlin
package com.apc.its.ecm.sandbox.model

import io.github.mmbishop.gwttest.core.GwtTest
import io.github.mmbishop.gwttest.functions.GwtFunction
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument
import io.github.mmbishop.gwttest.model.Context
import org.junit.jupiter.api.Test

class DivisionTest {

    @Test
    fun quotient_of_two_numbers_is_calculated() {
        gwt.test()
            .given(a_dividend, 12.0)
            .and(a_divisor, 4.0)
            .`when`(dividing_the_dividend_by_the_divisor)
            .then(the_quotient_is, 3.0)
    }

    @Test
    fun dividing_a_number_by_zero_results_in_infinity() {
        gwt.test()
            .given(a_dividend, 6.0)
            .`when`(dividing_the_dividend_by_zero)
            .then(the_quotient_is_infinity)
    }

    private val gwt: GwtTest<TestContext> = GwtTest(TestContext::class.javaObjectType)

    private val a_dividend = GwtFunctionWithArgument<TestContext, Double> { context, dividend -> context.dividend = dividend }

    private val a_divisor = GwtFunctionWithArgument<TestContext, Double> { context, divisor -> context.divisor = divisor }

    private val dividing_the_dividend_by_the_divisor = GwtFunction<TestContext> { context ->
        context.quotient = context.dividend / context.divisor
    }

    private val dividing_the_dividend_by_zero = GwtFunction<TestContext> { context -> context.quotient = context.dividend / 0.0 }

    private val the_quotient_is = GwtFunctionWithArgument<TestContext, Double> { context, quotient ->
        assert(quotient == context.quotient)
    }

    private val the_quotient_is_infinity = GwtFunction<TestContext> { context -> assert(context.quotient.isInfinite()) }

    companion object {
        class TestContext : Context() {
            var dividend: Double = 0.0
            var divisor: Double = 0.0
            var quotient: Double = 0.0
        }
    }
}
```