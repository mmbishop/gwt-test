package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.core.exceptions.ExpectedExceptionNotThrownException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

public class ExecutedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;

    public ExecutedGwtTest(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Then clause
     * @return this {@code AssertedGwtTest} object
     */
    @SafeVarargs
    public final AssertedGwtTest<T> then(GwtFunction<T>... gwtFunctions) {
        ifExpectedExceptionWasDeclaredButNotThrownThenFailTheTest();
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        return new AssertedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    @SafeVarargs
    public final <V> AssertedGwtTest<T> then(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        ifExpectedExceptionWasDeclaredButNotThrownThenFailTheTest();
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return new AssertedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code AssertedGwtTest} object
     */
    public final <V> AssertedGwtTest<T> then(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        ifExpectedExceptionWasDeclaredButNotThrownThenFailTheTest();
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return new AssertedGwtTest<>(context);
    }

    private void ifExpectedExceptionWasDeclaredButNotThrownThenFailTheTest() {
        if (context.expectedExceptionClass != null && context.thrownException == null) {
            throw new ExpectedExceptionNotThrownException(context.expectedExceptionClass);
        }
    }

}
