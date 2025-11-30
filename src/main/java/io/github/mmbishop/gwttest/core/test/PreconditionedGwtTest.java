package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

public class PreconditionedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;

    public PreconditionedGwtTest(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
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

    /**
     * Invokes the given function with the context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then
     * @return this {@code PreconditionedGwtTest} object
     */
    public final PreconditionedGwtTest<T> and(GwtFunction<T> gwtFunction) {
        functionInvoker.invokeGwtFunctions(gwtFunction);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @return this {@code PreconditionedGwtTest} object
     */
    public final <V> PreconditionedGwtTest<T> and(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        functionInvoker.invokeGwtFunction(gwtFunction, arg);
        return this;
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of an And clause attached
     *                                       to a Given, When or Then.  This function takes an argument of type {@code V}
     *                                       and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @return this {@code PreconditionedGwtTest} object
     */
    @SafeVarargs
    public final <V> PreconditionedGwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }

}
