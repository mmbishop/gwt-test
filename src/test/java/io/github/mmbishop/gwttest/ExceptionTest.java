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

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionTest {

    private final GwtTest<ExceptionContext> gwt = new GwtTest<>(ExceptionContext.class);

    @Test
    void exception_is_thrown() {
        gwt.test()
                .when(doing_something)
                .then(an_exception_is_thrown);
    }

    private final GwtFunction<ExceptionContext> doing_something = context -> {
        throw new RuntimeException("An error occurred while doing something.");
    };

    private final GwtFunction<ExceptionContext> an_exception_is_thrown = context -> assertNotNull(context.thrownException);

    public static class ExceptionContext extends Context {}

}
