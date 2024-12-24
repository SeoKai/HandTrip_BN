package TeamGoat.TripSupporter.Controller.Location.Util;

import TeamGoat.TripSupporter.Exception.Location.*;
import TeamGoat.TripSupporter.Exception.IllegalPageRequestException;
import TeamGoat.TripSupporter.Exception.IllegalSortRequestException;

import java.util.Set;

public class LocationControllerValidator {

    public static void validatePageRequest(int page, String sortValue, String sortDirection) {
        if(page < 0 ){
            throw new IllegalPageRequestException("페이지값이 잘못되었습니다.");
        }
        if (!"userRatingsTotal".equals(sortValue) && !"googleRating".equals(sortValue)) {
            throw new IllegalSortRequestException("잘못된 정렬 기준입니다.");
        }
        if (!"asc".equals(sortDirection) && !"desc".equals(sortDirection)) {
            throw new IllegalSortRequestException("잘못된 정렬 방향입니다.");
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
            if(latitude < -90 || latitude > 90 ){
                throw new LocationInvalidLatitudeException("위도값은 -90~90사이의 값이여야 합니다.");
            }
            if(longitude < -180 || longitude > 180){
                throw new LocationInvalidLongitudeException("경도값은 -180~180사이의 값이여야 합니다.");
            }
        }
    }

    public static void validateDistance(Double distance) {
        if(distance < 0){
            throw new LocationInvalidDistanceException("잘못된 반경 값입니다.");
        }
    }



    public static void validateTagNames(Set<String> tagNames) {
        if(tagNames.size() > 3){
            throw new LocationInvalidTagNamesException("태그 값이 너무 많습니다. 태그는 최대 3개까지만 가능합니다.");
        }
    }

    public static void validateSortRequest(String sortValue, String sortDirection) {

        if (!"reviewCreatedAt".equals(sortValue) && !"googleRating".equals(sortValue)) {
            throw new IllegalSortRequestException("잘못된 정렬 기준입니다.");
        }
        if (!"asc".equals(sortDirection) && !"desc".equals(sortDirection)) {
            throw new IllegalSortRequestException("잘못된 정렬 방향입니다.");
        }
    }

    public static void validateLocationId(Long locationId) {
        if(locationId == null || locationId < 0){
            throw new IllegalLocationIdException("잘못된 지역 ID 값 입니다.");
        }
    }
}
