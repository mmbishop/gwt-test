package io.github.mmbishop.gwttest.core;

public class ExpectedExceptionNotThrownException extends RuntimeException {

    public ExpectedExceptionNotThrownException(Class<? extends Throwable> expectedExceptionClass) {
        super("Expected exception of type " + expectedExceptionClass.getName() + " not thrown.");
    }
}
