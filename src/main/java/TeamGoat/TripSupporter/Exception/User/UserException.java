package TeamGoat.TripSupporter.Exception.User;

public class UserException extends RuntimeException {
    public UserException(String message, Exception cause) {
        super(message,cause);
    }
}
