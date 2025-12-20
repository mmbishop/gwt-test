package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;

public class InitializingGwtTest<T extends Context> {

    private final FunctionInvoker<T> functionInvoker;

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
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code InitializingGwtTest} object
     */
    public final <V> InitializingGwtTest<T> given(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code InitializingGwtTest} object
     */
    @SafeVarargs
    public final <V> InitializingGwtTest<T> given(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code InitializingGwtTest} object
     */
    public final InitializingGwtTest<T> and(GwtFunction<T> gwtFunction) {
        functionInvoker.invokeGwtFunctions(gwtFunction);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code InitializingGwtTest} object
     */
    public final <V> InitializingGwtTest<T> and(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code InitializingGwtTest} object
     */
    @SafeVarargs
    public final <V> InitializingGwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }
}
