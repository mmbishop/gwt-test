package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

public class ConstructedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;

    public ConstructedGwtTest(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
    }

    public ConstructedGwtTest<T> expectingException(Class<? extends Throwable> expectedExceptionClass) {
        context.expectedExceptionClass = expectedExceptionClass;
        return this;
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the Given clause
     * @return this {@code PreconditionedGwtTest} object
     */
    @SafeVarargs
    public final PreconditionedGwtTest<T> given(GwtFunction<T>... gwtFunctions) {
        context.testPhase = TestPhase.GIVEN;
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        return new PreconditionedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code PreconditionedGwtTest} object
     */
    public final <V> PreconditionedGwtTest<T> given(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        context.testPhase = TestPhase.GIVEN;
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return new PreconditionedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the Given clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code PreconditionedGwtTest} object
     */
    @SafeVarargs
    public final <V> PreconditionedGwtTest<T> given(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        context.testPhase = TestPhase.GIVEN;
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return new PreconditionedGwtTest<>(context);
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final ExecutedGwtTest<T> when(GwtFunction<T>... gwtFunctions) {
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunctions(gwtFunctions);
        return new ExecutedGwtTest<>(context);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code ExecutedGwtTest} object
     */
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return new ExecutedGwtTest<>(context);
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
        context.testPhase = TestPhase.WHEN;
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return new ExecutedGwtTest<>(context);
    }

}
