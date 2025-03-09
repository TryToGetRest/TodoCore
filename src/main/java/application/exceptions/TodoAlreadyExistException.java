package application.exceptions;

public class TodoAlreadyExistException extends RuntimeException {
    public TodoAlreadyExistException(String message) {
        super(message);
    }
}
