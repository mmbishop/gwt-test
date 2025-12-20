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

package io.github.mmbishop.gwttest.functions;

import io.github.mmbishop.gwttest.model.Context;

/**
 * A function that is invoked by the GWT {@code given}, {@code when}, {@code then} or
 * {@code and} method. This function takes two arguments: a comma-separated list of value arguments of type {@code V} and an
 * instance of a {@link Context} subclass.
 * @param <V> value argument class
 * @param <T> subclass of {@link Context} declared in the test class in which this function exists
 */
@FunctionalInterface
public interface GwtFunctionWithArguments<T extends Context, V> {

    /**
     * Applies this function to the given context and arguments.
     * <p>
     * This method is invoked by the GWT test framework when executing {@code given}, {@code when},
     * {@code then}, or {@code and} clauses that require multiple value arguments.
     *
     * @param context the test context containing fields and state used during test execution
     * @param args    variable number of arguments of type {@code V} to be used by this function
     */
    void apply(T context, V... args);

}
