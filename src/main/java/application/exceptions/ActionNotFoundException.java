package application.exceptions;

public class ActionNotFoundException extends RuntimeException {
    public ActionNotFoundException(String message) {
        super(message);
    }
}
