package hr.algebra.utils.CustomExceptions;

public class CustomAlreadyExistsException extends RuntimeException{
    public CustomAlreadyExistsException(String message) {
        super(message);
    }
}