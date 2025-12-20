package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;

/**
 * Represents a Given-When-Then test that has preconditions (Given clauses) already defined.
 * <p>
 * This class is returned by {@link ConstructedGwtTest#given(GwtFunction[])} and related methods
 * after preconditions have been established. It provides methods to:
 * <ul>
 *   <li>Execute test actions using {@code when()} methods to transition to the When phase</li>
 *   <li>Chain additional preconditions using {@code and()} methods</li>
 * </ul>
 * <p>
 * The class supports method chaining to build fluent test definitions, and handles both
 * simple functions and functions with arguments for flexibility in test construction.
 * Once {@code when()} is invoked, the test transitions to the {@link ExecutedGwtTest} phase
 * where assertions can be made.
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class PreconditionedGwtTest<T extends Context> {

    private final FunctionInvoker<T> functionInvoker;
    private final WhenClauseExecutor<T> whenClauseExecutor;

    /**
     * Constructs a new PreconditionedGwtTest with the specified context.
     * <p>
     * This constructor initializes the function invoker and when clause executor
     * with the provided context, preparing the test for executing additional
     * preconditions or transitioning to the When phase.
     *
     * @param context the context object containing the fields used in test code
     */
    public PreconditionedGwtTest(T context) {
        this.functionInvoker = new FunctionInvoker<>(context);
        this.whenClauseExecutor = new WhenClauseExecutor<>(context);
    }

    /**
     * Invokes the given functions with the context object.
     * @param gwtFunctions {@code GwtFunction}s that contain logic to be performed as part of the When clause
     * @return an {@code ExecutedGwtTest} object
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
     * @param <V> the type of the argument
     * @return an {@code ExecutedGwtTest} object
     */
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        return whenClauseExecutor.executeWhenClause(gwtFunction, arg);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param args arguments of type {@code V}
     * @param <V> the type of the argument
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        return whenClauseExecutor.executeWhenClause(gwtFunction, args);
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
     * @param <V> the type of the argument
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
     * @param <V> the type of the argument
     * @return this {@code PreconditionedGwtTest} object
     */
    @SafeVarargs
    public final <V> PreconditionedGwtTest<T> and(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        functionInvoker.invokeGwtFunction(gwtFunction, args);
        return this;
    }

}
