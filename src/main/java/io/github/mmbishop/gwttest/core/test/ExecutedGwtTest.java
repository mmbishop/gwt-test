package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.core.exceptions.ExpectedExceptionNotThrownException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

/**
 * Represents a Given-When-Then test after the When clause has been executed.
 * This class provides methods to define the Then clause of the test, which contains
 * assertions and verification logic. It validates that any expected exceptions were
 * thrown during the When phase before proceeding to the Then phase.
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class ExecutedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;

    /**
     * Constructs an ExecutedGwtTest with the given context.
     * <p>
     * This constructor initializes the test in the executed state after the When clause
     * has been completed. It sets up the context and function invoker needed to execute
     * the Then clause assertions.
     *
     * @param context the context object containing test state and fields
     */
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
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        return new AssertedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code AssertedGwtTest} object
     */
    @SafeVarargs
    public final <V> AssertedGwtTest<T> then(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return new AssertedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Then clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code AssertedGwtTest} object
     */
    public final <V> AssertedGwtTest<T> then(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        context.testPhase = TestPhase.THEN;
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return new AssertedGwtTest<>(context);
    }

}
