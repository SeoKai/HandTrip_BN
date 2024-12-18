package TeamGoat.TripSupporter.Exception.Review;

public class ReviewDtoNullException extends RuntimeException  {

    public ReviewDtoNullException() {
        super("ReviewDto가 null입니다.");
    }

    public ReviewDtoNullException(String message) {
        super(message);
    }
}
