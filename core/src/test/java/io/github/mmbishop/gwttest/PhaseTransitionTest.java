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
import io.github.mmbishop.gwttest.core.MalformedTestException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class PhaseTransitionTest {

    private final GwtTest<PhaseTransitionTestContext> gwt = new GwtTest<>(PhaseTransitionTestContext.class);
    private MalformedTestException thrownException = null;

    @Test
    void test_executes_successfully_when_phases_are_called_in_the_correct_order() {
        gwt.test()
                .given(a_number)
                .when(squaring_the_number)
                .then(the_result_is_the_number_times_itself);
    }

    @Test
    void test_is_malformed_when_phases_are_not_called_in_the_correct_order() {
        try {
            gwt.test()
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself)
                    .given(a_number);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_GIVEN_is_called_multiple_times() {
        try {
            gwt.test()
                    .given(a_number)
                    .given(a_number)
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_WHEN_is_called_multiple_times() {
        try {
            gwt.test()
                    .given(a_number)
                    .when(squaring_the_number)
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_THEN_is_called_multiple_times() {
        try {
            gwt.test()
                    .given(a_number)
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself)
                    .then(the_result_is_the_number_times_itself);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_AND_is_called_first() {
        try {
            gwt.test()
                    .and(a_number)
                    .given(a_number)
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself);;
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_TEST_is_called_multiple_times() {
        try {
            gwt.test()
                    .given(a_number)
                    .when(squaring_the_number)
                    .test()
                    .then(the_result_is_the_number_times_itself);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    @Test
    void test_is_malformed_when_TEST_is_called_twice_at_the_beginning_of_the_test() {
        try {
            gwt.test()
                    .test()
                    .given(a_number)
                    .when(squaring_the_number)
                    .then(the_result_is_the_number_times_itself);
        }
        catch (MalformedTestException e) {
            thrownException = e;
        }
        finally {
            assertThat(thrownException, is(not(nullValue())));
        }
    }

    private final GwtFunction<PhaseTransitionTestContext> a_number = context -> context.number = 7;

    private final GwtFunction<PhaseTransitionTestContext> squaring_the_number = context -> context.result = context.number * context.number;

    private final GwtFunction<PhaseTransitionTestContext> the_result_is_the_number_times_itself = context ->
            assertThat(context.result, is(49));

    public static class PhaseTransitionTestContext extends Context {
        Integer number;
        Integer result;

        public PhaseTransitionTestContext() {
            number = 7;
        }
    }
}
