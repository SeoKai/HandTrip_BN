package TeamGoat.TripSupporter.Exception.Review;

public class ReviewCommentNullException extends RuntimeException  {

    public ReviewCommentNullException() {
        super("Review Comment가 null입니다.");
    }

    public ReviewCommentNullException(String message) {
        super(message);
    }
}
