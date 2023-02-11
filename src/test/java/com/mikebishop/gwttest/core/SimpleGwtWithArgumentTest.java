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
import com.mikebishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleGwtWithArgumentTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void simple_gwt_test_with_double_argument_is_executed() {
        gwt.test()
                .given(the_double_precision_number, 8.0)
                .when(squaring_the_double_precision_number)
                .then(the_double_precision_result_is, 64.0);
    }

    @Test
    void simple_gwt_test_with_integer_argument_is_executed() {
        gwt.test()
                .given(the_integer, 9)
                .when(squaring_the_integer)
                .then(the_integer_result_is, 81);
    }

    @Test
    void simple_gwt_test_with_string_argument_is_executed() {
        gwt.test()
                .given(the_string, "Hello")
                .when(concatenating_the_string, " World")
                .then(the_resulting_string_is, "Hello World");
    }

    @Test
    void simple_gwt_test_with_object_argument_is_executed() {
        gwt.test()
                .given(the_point, new Point(3, 5))
                .when(translating_the_point_by, new Offset(1, 2))
                .then(the_resulting_point_is, new Point(4, 7));
    }

    private final GwtFunctionWithArgument<TestContext, Double> the_double_precision_number
            = (context, arg) -> context.doubleArgument = arg;

    private final GwtFunctionWithArgument<TestContext, Integer> the_integer = (context, arg) -> context.integerArgument = arg;

    private final GwtFunctionWithArgument<TestContext, String> the_string = (context, arg) -> context.stringArgument = arg;

    private final GwtFunctionWithArgument<TestContext, Point> the_point = (context, point) -> context.pointArgument = point;

    private final GwtFunction<TestContext> squaring_the_double_precision_number = context -> context.doubleResult = Math.pow(context.doubleArgument, 2.0);

    private final GwtFunction<TestContext> squaring_the_integer = context -> context.integerResult = (int) Math.pow((double) context.integerArgument, 2.0);

    private final GwtFunctionWithArgument<TestContext, String> concatenating_the_string
            = (context, arg) -> context.stringResult = context.stringArgument + arg;

    private final GwtFunctionWithArgument<TestContext, Offset> translating_the_point_by = (context, offset) -> {
        context.pointResult = new Point(context.pointArgument.x() + offset.dx(), context.pointArgument.y() + offset.dy());
    };

    private final GwtFunctionWithArgument<TestContext, Double> the_double_precision_result_is
            = (context, expectedResult) -> assertThat(context.doubleResult, is(expectedResult));

    private final GwtFunctionWithArgument<TestContext, Integer> the_integer_result_is
            = (context, expectedResult) -> assertThat(context.integerResult, is(expectedResult));

    private final GwtFunctionWithArgument<TestContext, String> the_resulting_string_is
            = (context, expectedString) -> assertThat(context.stringResult, is(expectedString));

    private final GwtFunctionWithArgument<TestContext, Point> the_resulting_point_is = (context, expectedPoint) -> {
        assertThat(context.pointResult.x(), is(expectedPoint.x()));
        assertThat(context.pointResult.y(), is(expectedPoint.y()));
    };

    static class TestContext extends Context {
        Double doubleArgument;
        Integer integerArgument;
        String stringArgument;
        Point pointArgument;
        Double doubleResult;
        Integer integerResult;
        String stringResult;
        Point pointResult;
    }

    private record Point(int x, int y) {}

    private record Offset(int dx, int dy) {}

}
