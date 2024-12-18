package TeamGoat.TripSupporter.Mapper.Review;

public class ReviewException extends RuntimeException {

    public ReviewException(String message) {
        super(message);
    }

    public ReviewException(String message,Throwable cause) {
        super(message,cause);
    }
}
