package io.github.mmbishop.gwttest;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BackgroundClauseTest {

    private final GwtTest<BackgroundContext> gwt = new GwtTest<>(BackgroundContext.class);

    @BeforeEach
    void establish_background() {
        gwt.background()
                .given(a_starting_value, 10);
    }

    @Test
    void we_can_raise_the_number_to_a_power() {
        gwt.test()
                .given(an_exponent, 2)
                .when(when_raising_the_number_to_the_exponent)
                .then(then_the_result_is, 100);
    }

    @Test
    void we_can_cut_it_in_half() {
        gwt.test()
                .when(cutting_it_in_half)
                .then(then_the_result_is, 5);
    }

    private final GwtFunctionWithArgument<BackgroundContext, Integer> a_starting_value =
            (context, arg) -> context.number = arg;

    private final GwtFunctionWithArgument<BackgroundContext, Integer> an_exponent =
            ((context, arg) -> context.exponent = arg);

    private final GwtFunction<BackgroundContext> when_raising_the_number_to_the_exponent =
            context -> context.result = (int) Math.pow(context.number, context.exponent);

    private final GwtFunction<BackgroundContext> cutting_it_in_half = context -> context.result = context.number / 2;

    private final GwtFunctionWithArgument<BackgroundContext, Integer> then_the_result_is =
            (context, expectedValue) -> assertThat(context.result, is(expectedValue));

    public static class BackgroundContext extends Context {
        int number;
        int exponent;
        int result;
    }
}
