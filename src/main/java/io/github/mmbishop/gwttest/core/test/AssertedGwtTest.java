package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;

public class AssertedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;
    private final WhenClauseExecutor<T> whenClauseExecutor;

    public AssertedGwtTest(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
        this.whenClauseExecutor = new WhenClauseExecutor<>(context);
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code AssertedGwtTest} object
     */
    public final AssertedGwtTest<T> and(GwtFunction<T> gwtFunction) {
        functionInvoker.invokeGwtFunctions(gwtFunction);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    public final <V> AssertedGwtTest<T> and(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    @SafeVarargs
    public final <V> AssertedGwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of a But clause attached
     *                                       to a Then
     * @return this {@code AssertedGwtTest} object
     */
    public final AssertedGwtTest<T> but(GwtFunction<T> gwtFunction) {
        functionInvoker.invokeGwtFunctions(gwtFunction);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of a But clause attached
     *                                       to a Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    public final <V> AssertedGwtTest<T> but(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of a But clause attached
     *                                       to a Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    @SafeVarargs
    public final <V> AssertedGwtTest<T> but(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final ExecutedGwtTest<T> when(GwtFunction<T>... gwtFunctions) {
        return whenClauseExecutor.executeWhenClause(gwtFunctions);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code ExecutedGwtTest} object
     */
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        return whenClauseExecutor.executeWhenClause(gwtFunction, arg);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        return whenClauseExecutor.executeWhenClause(gwtFunction, args);
    }

}
