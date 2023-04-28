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
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MultipleTestCasesTest {

    @Test
    void test_has_multiple_cases() {
        gwt.test()
                .given(numbers, 12, 4)
                .when(multiplying_the_numbers)
                .then(the_product_is, 48)
                .when(dividing_the_numbers)
                .then(the_quotient_is, 3);
    }

    private final GwtTest<MultipleCasesTestContext> gwt = new GwtTest<>(MultipleCasesTestContext.class);

    private final GwtFunctionWithArguments<MultipleCasesTestContext, Integer> numbers = (context, args) -> context.numbers = args;

    private final GwtFunction<MultipleCasesTestContext> multiplying_the_numbers
            = context -> context.result = context.numbers[0] * context.numbers[1];

    private final GwtFunction<MultipleCasesTestContext> dividing_the_numbers
            = context -> context.result = context.numbers[0] / context.numbers[1];

    private final GwtFunctionWithArgument<MultipleCasesTestContext, Integer> the_product_is
            = (context, expectedProduct) -> assertThat(context.result, is(expectedProduct));

    private final GwtFunctionWithArgument<MultipleCasesTestContext, Integer> the_quotient_is
            = (context, expectedQuotient) -> assertThat(context.result, is(expectedQuotient));

    public static class MultipleCasesTestContext extends Context {
        Integer[] numbers;
        Integer result;
    }
}
