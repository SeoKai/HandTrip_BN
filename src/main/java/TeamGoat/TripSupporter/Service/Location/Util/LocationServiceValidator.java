package TeamGoat.TripSupporter.Service.Location.Util;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Exception.Location.LocationMappingException;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.Location.LocationNullException;
import TeamGoat.TripSupporter.Exception.Location.LocationSortNullException;
import TeamGoat.TripSupporter.Exception.IllegalPageRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public class LocationServiceValidator {

    @ExceptionHandler
    public static void validateLocationEntity(List<Location> locationList) {
        if (locationList == null || locationList.isEmpty()) {
            throw new LocationNotFoundException("해당 지역에 대한 정보를 찾을 수 없습니다.");
        }
    }

    @ExceptionHandler
    public static void validateLocationEntity(Page<Location> locationPage) {
        if (locationPage == null || locationPage.isEmpty()) {
            throw new LocationNotFoundException("해당 지역에 대한 정보를 찾을 수 없습니다.");
        }
    }

    public static void validateLocationDto(Page<LocationResponseDto> locationDtoPage){
        if (locationDtoPage == null || locationDtoPage.isEmpty()){
            throw new LocationMappingException("해당 엔티티를 Dto로 변환하는대 실패하였습니다.");
        }
    }

    public static void validateLocationDto(List<LocationResponseDto> locationDtoList){
        if(locationDtoList == null || locationDtoList.isEmpty()){
            throw new LocationMappingException("해당 엔티티를 Dto로 변환하는대 실패하였습니다.");
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
