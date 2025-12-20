package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.core.exceptions.UnexpectedExceptionCaughtException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Utility class responsible for invoking GWT (Given-When-Then) functions with proper exception handling.
 * This class wraps function invocations to catch and handle exceptions, logging them and storing them
 * in the context for validation against expected exceptions.
 *
 * @param <T> a subclass of {@link Context} that contains the fields used in test code
 */
public class FunctionInvoker<T extends Context> {

    private static final Logger logger = LoggerFactory.getLogger(FunctionInvoker.class);

    private final T context;

    /**
     * Constructs a new FunctionInvoker with the specified context.
     * <p>
     * The provided context is used for all function invocations and stores any exceptions
     * that occur during test execution for later validation against expected exceptions.
     *
     * @param context the context object that contains the fields used in test code and
     *                maintains state across test phases
     */
    public FunctionInvoker(T context) {
        this.context = context;
    }

    /**
     * Invokes a GWT function that takes a single argument along with the context object.
     * <p>
     * This method executes the provided function with the given argument and the test context.
     * If an exception occurs during execution, it is caught, logged, and stored in the context's
     * {@code thrownException} field. The exception is then validated against any expected exception
     * type that may have been configured in the context. If the exception was not expected,
     * an {@link UnexpectedExceptionCaughtException} is thrown to fail the test.
     * <p>
     * This method is typically used internally by the GWT test framework to execute Given, When,
     * or Then clauses that require a single argument in addition to the context.
     *
     * @param <V>         the type of the argument passed to the function
     * @param gwtFunction the GWT function to invoke, which accepts the context and an argument of type V
     * @param arg         the argument to pass to the function
     * @throws UnexpectedExceptionCaughtException if an exception occurs that does not match
     *                                            the expected exception type configured in the context
     */
    public final <V> void invokeGwtFunction(GwtFunctionWithArgument<T, V> gwtFunction, V arg) {
        try {
            gwtFunction.apply(context, arg);
        }
        catch (Throwable e) {
            context.thrownException = e;
            logger.error(e.getMessage(), e);
            throwCaughtExceptionIfNotExpected(e);
        }
    }

    /**
     * Invokes a GWT function that takes multiple arguments along with the context object.
     * <p>
     * This method executes the provided function with the given varargs arguments and the test context.
     * If an exception occurs during execution, it is caught, logged, and stored in the context's
     * {@code thrownException} field. The exception is then validated against any expected exception
     * type that may have been configured in the context. If the exception was not expected,
     * an {@link UnexpectedExceptionCaughtException} is thrown to fail the test.
     * <p>
     * This method is typically used internally by the GWT test framework to execute Given, When,
     * or Then clauses that require multiple arguments in addition to the context.
     *
     * @param <V>         the type of the arguments passed to the function
     * @param gwtFunction the GWT function to invoke, which accepts the context and multiple arguments of type V
     * @param args        the varargs arguments to pass to the function
     * @throws UnexpectedExceptionCaughtException if an exception occurs that does not match
     *                                            the expected exception type configured in the context
     */
    @SafeVarargs
    public final <V> void invokeGwtFunction(GwtFunctionWithArguments<T, V> gwtFunction, V... args) {
        try {
            gwtFunction.apply(context, args);
        }
        catch (Throwable e) {
            context.thrownException = e;
            logger.error(e.getMessage(), e);
            throwCaughtExceptionIfNotExpected(e);
        }
    }

    /**
     * Invokes multiple GWT functions sequentially, each taking only the context object.
     * <p>
     * This method executes each of the provided functions in order with the test context.
     * All functions are invoked within a single try-catch block. If an exception occurs during
     * execution of any function, it is caught, logged, and stored in the context's
     * {@code thrownException} field. The exception is then validated against any expected exception
     * type that may have been configured in the context. If the exception was not expected,
     * an {@link UnexpectedExceptionCaughtException} is thrown to fail the test.
     * <p>
     * This method is typically used internally by the GWT test framework to execute multiple Given,
     * When, or Then clauses that each require only the context without additional arguments.
     *
     * @param gwtFunctions the varargs array of GWT functions to invoke, each accepting only the context
     * @throws UnexpectedExceptionCaughtException if an exception occurs that does not match
     *                                            the expected exception type configured in the context
     */
    @SafeVarargs
    public final void invokeGwtFunctions(GwtFunction<T>... gwtFunctions) {
        try {
            Arrays.stream(gwtFunctions).forEach(f -> f.apply(context));
        }
        catch (Throwable e) {
            context.thrownException = e;
            logger.error(e.getMessage(), e);
            throwCaughtExceptionIfNotExpected(e);
        }
    }

    private void throwCaughtExceptionIfNotExpected(Throwable e) {
        if (context.expectedExceptionClass == null || !context.expectedExceptionClass.equals(e.getClass())) {
            throw new UnexpectedExceptionCaughtException(e);
        }
    }
}
