package sallybot.exception;

/**
 * SallyException is a custom exception class that extends RuntimeException.
 * It is used to represent exceptions that occur in the SallyBot application. <br>
 * It provides a constructor that takes a message as a parameter,
 * which can be used to provide more information about the exception when it is thrown. <br>
 * This class serves as a base exception for all exceptions that may occur in the SallyBot application,
 * allowing for consistent error handling and messaging throughout the application.
 */
public class SallyException extends RuntimeException {
    /**
     * The constructor initializes the SallyException with the given message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public SallyException(String message) {
        super(message);
    }
}
