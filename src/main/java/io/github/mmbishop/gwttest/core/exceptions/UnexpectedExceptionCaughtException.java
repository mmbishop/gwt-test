package io.github.mmbishop.gwttest.core.exceptions;

public class UnexpectedExceptionCaughtException extends RuntimeException {

    public UnexpectedExceptionCaughtException(Throwable cause) {
        super("Unexpected exception of type " + cause.getClass() + " caught.");
    }
}
