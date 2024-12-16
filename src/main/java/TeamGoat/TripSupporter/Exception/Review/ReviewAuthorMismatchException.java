package TeamGoat.TripSupporter.Exception.Review;

public class ReviewAuthorMismatchException extends RuntimeException {

    public ReviewAuthorMismatchException() {
        super("작성자와 user가 일치하지 않습니다.");
    }

    public ReviewAuthorMismatchException(String message) {
        super(message);
    }
}
