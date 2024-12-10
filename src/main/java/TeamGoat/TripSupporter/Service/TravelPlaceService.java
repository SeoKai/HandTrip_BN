package TeamGoat.TripSupporter.Service;


import java.util.List;

// 여행지 탐색 및 검색 인터페이스
public interface TravelSearchService {

    /**
     * 테마별로 여행지를 검색합니다.
     *
     * @param tag 여행지 태그 (예: 랜드마크, 서브컬쳐, 쇼핑)
     * @return 검색된 여행지 목록
     */
    List<TravelPlaceDto> getPlacesByTheme(String tag);

    /**
     * 지역별로 여행지를 검색합니다.
     *
     * @param cityName 검색하려는 도시 이름
     * @return 해당 지역의 주요 여행지 목록
     */
    List<TravelPlaceDto> getPlacesByRegion(String cityName);

    /**
     * 인기순으로 여행지를 정렬하여 반환합니다.
     *
     * @return 인기순으로 정렬된 여행지 목록
     */
    List<TravelPlaceDto> getPopularPlaces();

    /**
     * 특정 여행지의 상세 정보를 조회합니다.
     *
     * @param placeId 여행지 ID
     * @return 여행지 상세 정보 DTO
     */
    TravelPlaceDto getPlaceDetails(Long placeId);
}
