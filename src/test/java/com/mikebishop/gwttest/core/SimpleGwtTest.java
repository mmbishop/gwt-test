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
import com.mikebishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleGwtTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void simple_gwt_test_is_executed() {
        gwt.test()
                .given(a_number)
                .when(squaring_the_number)
                .then(the_result_is_the_number_times_itself);
    }

    private final GwtFunction<TestContext> a_number = context -> context.number = 6.0;

    private final GwtFunction<TestContext> squaring_the_number = context -> context.result = Math.pow(context.number, 2);

    private final GwtFunction<TestContext> the_result_is_the_number_times_itself = context -> assertThat(context.result, is(36.0));

    static class TestContext extends Context {
        Double number;
        Double result;
    }

}
