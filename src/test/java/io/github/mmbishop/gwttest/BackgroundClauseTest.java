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
    void increment_the_value() {
        gwt.test()
                .when(incrementing_the_value)
                .then(then_the_result_is, 11);
    }

    @Test
    void decrement_the_value() {
        gwt.test()
                .when(decrementing_the_value)
                .then(then_the_result_is, 9);
    }

    private final GwtFunctionWithArgument<BackgroundContext, Integer> a_starting_value =
            (context, arg) -> context.number = arg;

    private final GwtFunction<BackgroundContext> incrementing_the_value = context -> context.number++;

    private final GwtFunction<BackgroundContext> decrementing_the_value = context -> context.number--;

    private final GwtFunctionWithArgument<BackgroundContext, Integer> then_the_result_is =
            (context, expectedValue) -> assertThat(context.number, is(expectedValue));

    public static class BackgroundContext extends Context {
        int number;
    }
}
