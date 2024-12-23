package TeamGoat.TripSupporter.Exception.Review;

public class ReviewRatingNullException extends RuntimeException {

    public ReviewRatingNullException() {
        super("Rating이 null입니다.");
    }

    public ReviewRatingNullException(String message) {
        super(message);
    }
}
