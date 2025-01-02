package TeamGoat.TripSupporter.Service.Location.Util;

public class PhotoUrlGenerator {

    public static String generatePhotoUrl(String photoReference) {
//        String apiKey = System.getenv("GOOGLE_API_KEY"); // 환경 변수에서 API 키 가져오기
        String apiKey = "AIzaSyA8aivSxIFr9kZQa0AhBcLlwbxSlXy7ICQ";
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=" + apiKey;
    }
}
