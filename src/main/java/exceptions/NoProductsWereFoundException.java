package exceptions;

public class NoProductsWereFoundException extends AppException {
    public NoProductsWereFoundException(String message) {
        super(message);
    }

    public NoProductsWereFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
