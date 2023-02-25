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

package io.github.mmbishop.gwttest.core;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

import java.util.Arrays;
import java.util.Optional;

/**
 * The core class for building Given-When-Then tests.
 * @param <T> a subclass of {@link Context} that contains the fields used in test code.
 */
public class GwtTest<T extends Context> {

    private TestPhaseValidator testPhaseValidator;
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
     * @throws TestConstructionException could not construct the test, most likely due to an inability to
     * instantiate the context class
     */
    public GwtTest<T> test() {
        try {
            context = contextClass.getDeclaredConstructor().newInstance();
            context.testName = getCallingMethodName();
            context.testPhase = null;
            testPhaseValidator = new TestPhaseValidator();
            return this;
        }
        catch (Exception e) {
            throw new TestConstructionException("Can't construct test", e);
        }
    }

    /**
     * Initializes the {@code GwtTest} object
     * @param testName the name of the test
     * @return this {@code GwtTest} object
     * @throws TestConstructionException could not construct the test, most likely due to an inability to
     * instantiate the context class
     */
    public GwtTest<T> test(String testName) {
        try {
            context = contextClass.getDeclaredConstructor().newInstance();
            context.testName = testName;
            context.testPhase = null;
            testPhaseValidator = new TestPhaseValidator();
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
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.GIVEN);
        context.testPhase = TestPhase.GIVEN;
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
    public final <V> GwtTest<T> given(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.GIVEN);
        context.testPhase = TestPhase.GIVEN;
        invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final <V> GwtTest<T> given(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.GIVEN);
        context.testPhase = TestPhase.GIVEN;
        invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final GwtTest<T> when(GwtFunction<T>... gwtFunctions) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.WHEN);
        context.testPhase = TestPhase.WHEN;
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
    public final <V> GwtTest<T> when(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.WHEN);
        context.testPhase = TestPhase.WHEN;
        invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final <V> GwtTest<T> when(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.WHEN);
        context.testPhase = TestPhase.WHEN;
        invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Then clause
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final GwtTest<T> then(GwtFunction<T>... gwtFunctions) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.THEN);
        context.testPhase = TestPhase.THEN;
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
    public final <V> GwtTest<T> then(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.THEN);
        context.testPhase = TestPhase.THEN;
        invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final <V> GwtTest<T> then(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        testPhaseValidator.validatePhaseTransition(context.testPhase, TestPhase.THEN);
        context.testPhase = TestPhase.THEN;
        invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code GwtTest} object
     */
    public final GwtTest<T> and(GwtFunction<T> gwtFunction) {
        testPhaseValidator.validateSelfTransition(context.testPhase);
        invokeGwtFunctions(gwtFunction);
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
    public final <V> GwtTest<T> and(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        testPhaseValidator.validateSelfTransition(context.testPhase);
        invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code GwtTest} object
     */
    @SafeVarargs
    public final <V> GwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        testPhaseValidator.validateSelfTransition(context.testPhase);
        invokeGwtFunction(gwtFunction, args);
        return this;
    }

    private <V> void invokeGwtFunction(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        try {
            gwtFunction.apply(context, arg);
        }
        catch (Exception e) {
            context.thrownException = e;
        }
    }

    @SafeVarargs
    private <V> void invokeGwtFunction(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        try {
            gwtFunction.apply(context, args);
        }
        catch (Exception e) {
            context.thrownException = e;
        }
    }

    @SafeVarargs
    private void invokeGwtFunctions(GwtFunction<T>... gwtFunctions) {
        try {
            Arrays.stream(gwtFunctions).forEach(f -> f.apply(context));
        }
        catch (Exception e) {
            context.thrownException = e;
        }
    }

    private String getCallingMethodName() {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Optional<String> callingMethodName = stackWalker.walk(frames -> frames
                .skip(2) // first frame is this method, second frame is the method calling this method
                .findFirst()
                .map(StackWalker.StackFrame::getMethodName));
        return callingMethodName.orElse("unknown");
    }

}
