package io.github.mmbishop.gwttest.core;

public class UnexpectedExceptionCaughtException extends RuntimeException {

    public UnexpectedExceptionCaughtException(Throwable cause) {
        super("Unexpected exception of type " + cause.getClass() + " caught.");
    }
}
