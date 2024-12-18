package TeamGoat.TripSupporter.Mapper.Review;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException() {
        super("location을 찾을 수 없습니다.");
    }


    public LocationNotFoundException(String message) {
        super(message);
    }
}
