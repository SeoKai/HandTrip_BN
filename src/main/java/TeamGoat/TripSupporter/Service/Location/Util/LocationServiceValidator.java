package TeamGoat.TripSupporter.Service.Location.Util;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Exception.Location.LocationMappingException;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.Location.LocationSortNullException;
import TeamGoat.TripSupporter.Exception.IllegalPageRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class LocationServiceValidator {


    public static void validateLocationEntity(List<?> locationList) {

        if (locationList == null || locationList.isEmpty()) {
            throw new LocationNotFoundException("해당 지역에 대한 정보를 찾을 수 없습니다.");
        }
    }


    public static void validateLocationEntity(Page<?> locationPage) {

        if (locationPage == null || locationPage.isEmpty()) {
            throw new LocationNotFoundException("해당 지역에 대한 정보를 찾을 수 없습니다.");
        }
    }
    public static void validateLocationEntity(Location location) {
        if (location == null) {
            throw new LocationNotFoundException("해당 지역에 대한 정보를 찾을 수 없습니다.");
        }
    }



    public static void validateLocationDto(Page<?> locationDtoPage){
        if (locationDtoPage == null || locationDtoPage.isEmpty()){
            throw new LocationMappingException("해당 지역 Data를 찾는대 실패하였습니다 또는 변환이 제대로 이루어지지 않았습니다.");
        }
    }

    public static void validateLocationDto(List<?> locationDtoList){

        if(locationDtoList == null || locationDtoList.isEmpty()){
            throw new LocationMappingException("해당 지역 Data를 찾는대 실패하였습니다 또는 변환이 제대로 이루어지지 않았습니다.");

        }
    }

    public static void validateLocationDto(LocationDto locationDto){
        if(locationDto == null){
            throw new LocationNotFoundException("해당 지역 Data를 찾는대 실패하였습니다 또는 변환이 제대로 이루어지지 않았습니다.");
        }
    }

    public static void validateLocationSort(Sort sort){
        if(sort == null || sort.isEmpty()){
            throw new LocationSortNullException("정렬 기준이 잘못되었습니다.");
        }
    }

    public static void validatePageable(Pageable pageable) {

        if (pageable == null) {
            throw new IllegalPageRequestException("pageable이 null입니다.");
        }

        if (pageable.getPageNumber() < 0) {
            throw new IllegalPageRequestException("현재 페이지는 0 이상이어야 합니다.");
        }

        if (pageable.getOffset() < 0) {
            throw new IllegalPageRequestException("조회 위치는 0 이상이어야 합니다.");
        }
    }

}
