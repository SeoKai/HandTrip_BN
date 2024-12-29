package TeamGoat.TripSupporter.Service.Location.Util;

public class PhotoUrlGenerator {

    public static String generatePhotoUrl(String photoReference) {
//        String apiKey = System.getenv("GOOGLE_API_KEY"); // 환경 변수에서 API 키 가져오기
        String apiKey = "AIzaSyDYcmeImkXId7f8bF5GT1N3bWEDAhnp-rM";
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=" + apiKey;
    }
}
