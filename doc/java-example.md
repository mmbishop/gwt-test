### gwt-test example - Java

```java
package gwttest.example;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DivisionTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void quotient_of_two_numbers_is_calculated() {
        gwt.test()
                .given(a_dividend, 12.0)
                .and(a_divisor, 4.0)
                .when(dividing_the_dividend_by_the_divisor)
                .then(the_quotient_is, 3.0);
    }

    @Test
    void dividing_a_number_by_zero_results_in_infinity() {
        gwt.test()
                .given(a_dividend, 6.0)
                .when(dividing_the_dividend_by_zero)
                .then(the_quotient_is_infinity);
    }

    private final GwtFunctionWithArgument<TestContext, Double> a_dividend  
            = (context, dividend) -> context.dividend = dividend;

    private final GwtFunctionWithArgument<TestContext, Double> a_divisor  
            = (context, divisor) -> context.divisor = divisor;

    private final GwtFunction<TestContext> dividing_the_dividend_by_the_divisor  
            = context -> context.quotient = context.dividend / context.divisor;
    
    private final GwtFunction<TestContext> dividing_the_dividend_by_zero  
            = context -> context.quotient = context.dividend / 0.0;

    private final GwtFunctionWithArgument<TestContext, Double> the_quotient_is  
            = (context, expectedQuotient) -> assertThat(context.quotient, is(expectedQuotient));
    
    private final GwtFunction<TestContext> the_quotient_is_infinity  
            = context -> assertThat(context.quotient, is(Double.POSITIVE_INFINITY));

    public static final class TestContext extends Context {
        Double dividend;
        Double divisor;
        Double quotient;
    }
}
```
