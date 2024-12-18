package TeamGoat.TripSupporter.Mapper.Review;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super("review를 찾을 수 없습니다.");
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

}

