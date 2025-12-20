package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.core.exceptions.MalformedTestException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;

/**
 * Represents a GWT (Given-When-Then) test in its initialization phase, typically used for Background clauses.
 * <p>
 * This class provides methods to set up preconditions using {@code given()} and {@code and()} clauses before
 * the main test execution begins. It allows chaining multiple setup functions together in a fluent interface style.
 * An {@code InitializingGwtTest} is created when the {@link io.github.mmbishop.gwttest.core.GwtTest#background()}
 * method is called, enabling the definition of shared setup logic that can be reused across multiple tests.
 * </p>
 * <p>
 * This class supports three types of functions:
 * <ul>
 *   <li>{@link GwtFunction} - functions that take only a context object</li>
 *   <li>{@link GwtFunctionWithArgument} - functions that take a single argument and a context object</li>
 *   <li>{@link GwtFunctionWithArguments} - functions that take multiple arguments and a context object</li>
 * </ul>
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class InitializingGwtTest<T extends Context> {

    private final FunctionInvoker<T> functionInvoker;
    private boolean givenFound = false;

    /**
     * Constructs a new {@code InitializingGwtTest} instance for setting up test preconditions.
     * <p>
     * This constructor is typically called internally when {@link io.github.mmbishop.gwttest.core.GwtTest#background()}
     * is invoked. It initializes the function invoker with the provided context, which will be used to execute
     * setup functions during the initialization phase of the test.
     * </p>
     *
     * @param context the context object that holds the state and data for the test initialization
     */
    public InitializingGwtTest(T context) {
        this.functionInvoker = new FunctionInvoker<>(context);
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Given clause
     * @return this {@code InitializingGwtTest} object
     */
    @SafeVarargs
    public final InitializingGwtTest<T> given(GwtFunction<T>... gwtFunctions) {
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        givenFound = true;
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code InitializingGwtTest} object
     */
    public final <V> InitializingGwtTest<T> given(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        givenFound = true;
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code InitializingGwtTest} object
     */
    @SafeVarargs
    public final <V> InitializingGwtTest<T> given(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        givenFound = true;
        return this;
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code InitializingGwtTest} object
     */
    public final InitializingGwtTest<T> and(GwtFunction<T> gwtFunction) {
        if (!givenFound) {
            throw new MalformedTestException("An And clause cannot be attached to a background without a preceding Given clause.");
        }

        functionInvoker.invokeGwtFunctions(gwtFunction);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code InitializingGwtTest} object
     */
    public final <V> InitializingGwtTest<T> and(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        if (!givenFound) {
            throw new MalformedTestException("An And clause cannot be attached to a background without a preceding Given clause.");
        }

        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code InitializingGwtTest} object
     */
    @SafeVarargs
    public final <V> InitializingGwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        if (!givenFound) {
            throw new MalformedTestException("An And clause cannot be attached to a background without a preceding Given clause.");
        }

        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }
}
