package io.github.mmbishop.gwttest;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class ButClauseTest {

    private final GwtTest<ButClauseTestContext> gwt = new GwtTest<>(ButClauseTestContext.class);

    @Test
    void gwt_test_with_but_clause_is_executed() {
        gwt.test()
                .given(a_positive_number)
                .when(dividing_the_number_by_two)
                .then(the_quotient_is_smaller_than_the_number)
                .but(is_still_positive);
    }

    private final GwtFunction<ButClauseTestContext> a_positive_number = context -> context.number = 6;

    private final GwtFunction<ButClauseTestContext> dividing_the_number_by_two =
            context -> context.quotient = context.number / 2;

    private final GwtFunction<ButClauseTestContext> the_quotient_is_smaller_than_the_number =
            context -> assertThat(context.quotient, lessThan(context.number));

    private final GwtFunction<ButClauseTestContext> is_still_positive =
            context -> assertThat(context.quotient, greaterThan(0));

    public static class ButClauseTestContext extends Context {
        Integer number;
        Integer quotient;
    }
}
