package TeamGoat.TripSupporter.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("user를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
