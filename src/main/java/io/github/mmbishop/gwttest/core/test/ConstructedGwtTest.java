package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;

/**
 * Represents a constructed Given-When-Then test that is ready to execute test clauses.
 * <p>
 * This class is returned by {@link io.github.mmbishop.gwttest.core.GwtTest#test()} or
 * {@link io.github.mmbishop.gwttest.core.GwtTest#test(String)} and provides methods to:
 * <ul>
 *   <li>Declare expected exceptions via {@link #expectingException(Class)}</li>
 *   <li>Define preconditions using {@code given()} methods</li>
 *   <li>Execute actions using {@code when()} methods</li>
 * </ul>
 * <p>
 * The class supports method chaining to build fluent test definitions, and handles both
 * simple functions and functions with arguments for flexibility in test construction.
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class ConstructedGwtTest<T extends Context> {

    private final T context;
    private final FunctionInvoker<T> functionInvoker;
    private final WhenClauseExecutor<T> whenClauseExecutor;

    /**
     * Constructs a new ConstructedGwtTest instance with the provided context.
     * <p>
     * This constructor initializes the test framework components needed to execute
     * Given-When-Then test clauses, including the function invoker for executing
     * test functions and the when clause executor for handling test actions.
     *
     * @param context the context object of type {@code T} that contains the fields
     *                and state used throughout the test execution
     */
    public ConstructedGwtTest(T context) {
        this.context = context;
        this.functionInvoker = new FunctionInvoker<>(context);
        this.whenClauseExecutor = new WhenClauseExecutor<>(context);
    }

    /**
     * Declares that the test expects a specific exception to be thrown during execution.
     * <p>
     * This method should be called before the {@code when()} clause to indicate that
     * the test action is expected to throw an exception of the specified type. If the
     * expected exception is not thrown, or if a different exception is thrown, the test
     * will fail.
     *
     * @param expectedExceptionClass the class of the exception expected to be thrown
     * @return this {@code ConstructedGwtTest} object for method chaining
     */
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
     * @param <V> the type of the argument
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
     * @param <V> the type of the argument
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
        return whenClauseExecutor.executeWhenClause(gwtFunctions);
    }

    /**
     * Invokes the given function on the given argument and context object.
     * @param gwtFunction {@code GwtFunction} that contains logic to be performed as part of the When clause. This function takes
     *                                       an argument of type {@code V} and an instance of a subclass of {@link Context}.
     * @param arg an argument of type {@code V}
     * @param <V> the type of the argument
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
     * @param <V> the type of the argument
     * @return this {@code ExecutedGwtTest} object
     */
    @SafeVarargs
    public final <V> ExecutedGwtTest<T> when(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        return whenClauseExecutor.executeWhenClause(gwtFunction, args);
    }

}
