package customExceptions;

/**
 * Custom exceptions defined in this class.
 */
public class FilterByTimeException extends Exception {

    /**
     * Defines custom exception. thrown when user inputs data to filter by incorrectly.
     *
     * @param message;
     */
    public FilterByTimeException(String message) {
        super(message);
    }
}
