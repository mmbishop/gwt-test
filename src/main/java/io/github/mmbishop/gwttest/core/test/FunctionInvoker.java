package io.github.mmbishop.gwttest.core.test;

import io.github.mmbishop.gwttest.core.exceptions.UnexpectedExceptionCaughtException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class FunctionInvoker<T extends Context> {

    private static final Logger logger = LoggerFactory.getLogger(FunctionInvoker.class);

    private final T context;

    public FunctionInvoker(T context) {
        this.context = context;
    }

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
