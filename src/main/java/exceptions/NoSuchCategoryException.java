package exceptions;

public class NoSuchCategoryException extends AppException {
    public NoSuchCategoryException(String message) {
        super(message);
    }

    public NoSuchCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
