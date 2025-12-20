package io.github.mmbishop.gwttest.core.exceptions;

/**
 * An exception that is thrown when an unexpected exception is caught during test execution.
 * <p>
 * This runtime exception wraps the unexpected exception and provides information about
 * its type in the error message. It is typically used in GWT tests when an exception
 * occurs that was not anticipated or expected as part of the test scenario.
 */
public class UnexpectedExceptionCaughtException extends RuntimeException {

    /**
     * Constructs a new UnexpectedExceptionCaughtException with the specified cause.
     * <p>
     * The exception message will include the class type of the caught exception.
     *
     * @param cause the unexpected exception that was caught
     */
    public UnexpectedExceptionCaughtException(Throwable cause) {
        super("Unexpected exception of type " + cause.getClass() + " caught.");
    }
}
