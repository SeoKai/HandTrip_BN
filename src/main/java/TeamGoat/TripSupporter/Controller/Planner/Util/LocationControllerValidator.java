package TeamGoat.TripSupporter.Controller.Planner.Util;

import TeamGoat.TripSupporter.Exception.Location.*;

import java.util.List;

public class LocationControllerValidator {

    public static void validateKeyword(String keyword) {
        if(keyword==null){
            throw new LocationSearchKeywordNullException("잘못된 검색어 입니다. 다시 입력해주세요");
        }
    }

    public static void validatePageRequest(int page, String sortValue, String sortDirection) {
        if(page < 0 || sortValue == null || sortDirection == null){
            throw new LocationPageRequestNullException("페이지또는 정렬값이 잘못되었습니다.");
        }
    }

    public static void validateRegion(Long regionId) {
        if(regionId < 0){
            throw new LocationRegionIdException("잘못된 지역값입니다.");
        }
    }

    public static void validateLatAndLon(Double latitude, Double longitude) {
        if(latitude == null || longitude == null){
            throw new LocationLatAndLonNullException("위도, 경도 값이 잘 못 되었습니다.");
        }else{
            if(latitude >= -90 && latitude <= 90 ){
                throw new LocationInvalidLatitudeException("위도값은 -90~90사이의 값이여야 합니다.");
            }
            if(longitude >= -180 && longitude <= 180){
                throw new LocationInvalidLongitudeException("경도값은 -180~180사이의 값이여야 합니다.");
            }
        }
    }

    public static void validateDistance(Double distance) {
        if(distance < 0){
            throw new LocationInvalidDistanceException("잘못된 반경 값입니다.");
        }
    }

    public static void validateSortRequest(String sortValue, String sortDirection) {
        if(sortValue == null || sortDirection == null){
            throw new LocationSortNullException("정렬값이 잘못되었습니다.");
        }
    }

    public static void validateTagNames(String[] tagNames) {
        if(tagNames == null || tagNames.length == 0){
            throw new LocationInvalidTagNamesException("태그 값이 없습니다.");
        }
        if(tagNames.length > 3){
            throw new LocationInvalidTagNamesException("태그 값이 너무 많습니다. 태그는 최대 3개까지만 가능합니다.");
        }
    }
}
