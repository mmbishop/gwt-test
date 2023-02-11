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

package com.mikebishop.gwttest.core;

import com.mikebishop.gwttest.functions.GwtFunction;
import com.mikebishop.gwttest.functions.GwtFunctionWithArgument;
import com.mikebishop.gwttest.functions.GwtFunctionWithArguments;
import com.mikebishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static com.mikebishop.gwttest.core.GwtTest.and;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleGwtWithArgumentsTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void multiple_arguments_test() {
        gwt.test()
                .given(numbers, 2, 3, and(4))
                .when(multiplying_the_numbers_together)
                .then(the_calculated_product_is, 24);
    }

    private final GwtFunctionWithArguments<TestContext, Integer> numbers = (context, numbers) -> {
        context.numbers = numbers;
    };

    private final GwtFunction<TestContext> multiplying_the_numbers_together = context -> {
        context.product = 1;
        for (Integer number : context.numbers) {
            context.product *= number;
        }
    };

    private final GwtFunctionWithArgument<TestContext, Integer> the_calculated_product_is = (context, expectedResult) -> {
        assertThat(context.product, is(expectedResult));
    };

    static class TestContext extends Context {
        Integer[] numbers;
        Integer product;
    }

}
