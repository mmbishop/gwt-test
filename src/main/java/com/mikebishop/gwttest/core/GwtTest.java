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

import java.util.Arrays;

/**
 * The core class for building Given-When-Then tests.
 * @param <T> a subclass of {@link Context} that contains the fields used in test code.
 */
public class GwtTest<T extends Context> {

    private final Class<T> contextClass;
    private T context;

    /**
     * Instantiates a {@code GwtTest} object.
     * @param contextClass the class of the context object used by the test
     */
    public GwtTest(Class<T> contextClass) {
        this.contextClass = contextClass;
    }

    /**
     * Initializes the {@code GwtTest} object
     * @return this {@code GwtTest} object
     */
    public GwtTest<T> test() {
        try {
            context = contextClass.getDeclaredConstructor().newInstance();
            return this;
        }
        catch (Exception e) {
            throw new TestConstructionException("Can't construct test", e);
        }
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Given clause
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final GwtTest<T> given(GwtFunction<T>... gwtFunctions) {
        invokeGwtFunctions(gwtFunctions);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code GwtTest} object
     */
    public final <V> GwtTest<T> given(GwtFunctionWithArgument<V, T> gwtFunction, V arg) {
        gwtFunction.apply(arg, context);
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final GwtTest<T> when(GwtFunction<T>... gwtFunctions) {
        invokeGwtFunctions(gwtFunctions);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code GwtTest} object
     */
    public final <V> GwtTest<T> when(GwtFunctionWithArgument<V, T> gwtFunction, V arg) {
        gwtFunction.apply(arg, context);
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Then clause
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final GwtTest<T> then(GwtFunction<T>... gwtFunctions) {
        invokeGwtFunctions(gwtFunctions);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code GwtTest} object
     */
    public final <V> GwtTest<T> then(GwtFunctionWithArgument<V, T> gwtFunction, V arg) {
        gwtFunction.apply(arg, context);
        return this;
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code GwtTest} object
     */
    public final GwtTest<T> and(GwtFunction<T> gwtFunction) {
        gwtFunction.apply(context);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code GwtTest} object
     */
    public final <V> GwtTest<T> and(GwtFunctionWithArgument<V, T> gwtFunction, V arg) {
        gwtFunction.apply(arg, context);
        return this;
    }

    @SafeVarargs
    private void invokeGwtFunctions(GwtFunction<T>... gwtFunctions) {
        Arrays.stream(gwtFunctions).forEach(f -> f.apply(context));
    }

}
