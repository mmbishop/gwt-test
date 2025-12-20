package io.github.mmbishop.gwttest.core.exceptions;

/**
 * Exception thrown when a test declares an expected exception via
 * {@link io.github.mmbishop.gwttest.core.test.ConstructedGwtTest#expectingException(Class)}
 * but that exception is not thrown during the execution of the test's {@code when()} clause.
 * <p>
 * This exception is used by the GWT test framework to enforce that when a test explicitly
 * declares it expects a particular exception type to be thrown, that expectation must be met
 * for the test to pass. If the test action completes without throwing the expected exception,
 * this exception is thrown to fail the test.
 * <p>
 * The exception message includes the fully qualified name of the expected exception class
 * that was not thrown, making it clear what the test was expecting.
 *
 * @see io.github.mmbishop.gwttest.core.test.ConstructedGwtTest#expectingException(Class)
 */
public class ExpectedExceptionNotThrownException extends RuntimeException {

    /**
     * Constructs a new exception indicating that an expected exception was not thrown during test execution.
     * <p>
     * This constructor creates an exception with a descriptive message that includes the fully qualified
     * name of the exception class that was expected but not thrown. The message follows the format:
     * "Expected exception of type [fully.qualified.ClassName] not thrown."
     *
     * @param expectedExceptionClass the class of the exception that was expected to be thrown but was not
     */
    public ExpectedExceptionNotThrownException(Class<? extends Throwable> expectedExceptionClass) {
        super("Expected exception of type " + expectedExceptionClass.getName() + " not thrown.");
    }
}
