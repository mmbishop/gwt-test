package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

/**
 * Executes the When clause of a Given-When-Then test. This class provides methods to execute
 * When clause logic with various types of functions and arguments, transitioning the test from
 * the Given phase to the When phase and preparing it for the Then phase.
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class WhenClauseExecutor<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;

    public WhenClauseExecutor(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
    }

    /**
     * Executes the when clause of a test on the given functions and context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return an {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final ExecutedGwtTest<T> executeWhenClause(GwtFunction<T>... gwtFunctions) {
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        return new ExecutedGwtTest<>(context);
    }

    /**
     * Executes the when clause of a test on the given function and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @param <V> the type of the argument
     * @return an {@code ExecutedGwtTest} object
     */
    public final <V> ExecutedGwtTest<T> executeWhenClause(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return new ExecutedGwtTest<>(context);
    }

    /**
     * Executes the when clause of a test on the given function and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final <V> ExecutedGwtTest<T> executeWhenClause(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return new ExecutedGwtTest<>(context);
    }
}
