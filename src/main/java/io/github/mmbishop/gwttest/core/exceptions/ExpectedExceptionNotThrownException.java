package io.github.mmbishop.gwttest.core.exceptions;

public class ExpectedExceptionNotThrownException extends RuntimeException {

    public ExpectedExceptionNotThrownException(Class<? extends Throwable> expectedExceptionClass) {
        super("Expected exception of type " + expectedExceptionClass.getName() + " not thrown.");
    }
}
