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

package io.github.mmbishop.gwttest;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MultipleFunctionsClauseTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void gwt_test_with_conjunction_is_executed() {
        gwt.test()
                .given(a_number)
                .and(another_number)
                .when(processing_the_numbers)
                .then(the_product_is_calculated)
                .and(the_remainder_is_calculated);
    }

    @Test
    void gwt_test_with_multiple_functions_in_given_and_then_is_executed() {
        gwt.test()
                .given(
                        a_number,
                        another_number
                )
                .when(processing_the_numbers)
                .then(
                        the_product_is_calculated,
                        the_remainder_is_calculated
                );
    }

    private final GwtFunction<TestContext> a_number = context -> context.number1 = 8;

    private final GwtFunction<TestContext> another_number = context -> context.number2 = 5;

    private final GwtFunction<TestContext> processing_the_numbers = context -> {
        context.product = context.number1 * context.number2;
        context.remainder = context.number1 % context.number2;
    };

    private final GwtFunction<TestContext> the_product_is_calculated = context -> assertThat(context.product, is(40));

    private final GwtFunction<TestContext> the_remainder_is_calculated = context -> assertThat(context.remainder, is(3));

    public static class TestContext extends Context {
        Integer number1;
        Integer number2;
        Integer product;
        Integer remainder;
    }

}
