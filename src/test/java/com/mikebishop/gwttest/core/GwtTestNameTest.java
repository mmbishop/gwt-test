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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GwtTestNameTest {

    private final GwtTest<NameTestContext> gwt = new GwtTest<>(NameTestContext.class);

    @Test
    void calling_method_name_is_stored_in_context_as_test_name_when_test_name_is_not_supplied() {
        gwt.test().then(calling_method_name_is_test_name);
    }

    @Test
    void supplied_test_name_is_stored_in_context() {
        String testName = "sample_test_name";
        gwt.test(testName).then(test_name_is, testName);
    }

    private final GwtFunction<NameTestContext> calling_method_name_is_test_name = context ->
            assertThat(context.testName, is("calling_method_name_is_stored_in_context_as_test_name_when_test_name_is_not_supplied"));

    private final GwtFunctionWithArgument<NameTestContext, String> test_name_is = (context, expectedTestName) ->
            assertThat(context.testName, is(expectedTestName));

    static class NameTestContext extends Context { }

}
