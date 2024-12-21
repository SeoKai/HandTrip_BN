package TeamGoat.TripSupporter.Repository.Location;


import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByRegionRegionId(Long regionId);

    /**
     * 여러가지 태그로 Location을 검색함 단일태그 검색도 가능
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     * @param tagNames  Set<String>으로 tagName을 받으므로 동적으로 값을 입력 받음
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return tags필드에 List<>tagNames를 가지고 있는 Location 데이터 (페이징 처리된 결과)
     */
    Page<Location> findByTags_TagNameInAndRegion_RegionId(Set<String> tagNames,Long regionId, Pageable pageable);  // 여러 태그 이름으로 장소 조회

    /**
     * locationName에 keyword가 포함된 Location 데이터를 페이징 처리하여 반환
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     * @param keyword 검색 keyword
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return locationName에 keyword가 포함된 Location 데이터 (페이징 처리된 결과)
     */
    Page<Location> findByLocationNameContaining(Long regionId, String keyword, Pageable pageable);

    /**
     * regionName에 해당하는 Region을 기준으로 Location 데이터를 페이징 처리하여 반환
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     * @param regionId 검색할 Region의 Id
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return regionName에 해당하는 Region에 속하는 Location 데이터 (페이징 처리된 결과)
     */
    Page<Location> findByRegion_RegionId(Long regionId, Pageable pageable);

//    위와 같은 메서드로 regionId를 받을지 region객체를 받을지 몰라서 일단 작성함
//    /**
//     * 특정 Region 객체를 기준으로 Location 데이터를 페이징 처리하여 반환
//     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
//     * @param region 검색할 Region 객체
//     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
//     * @return 주어진 Region에 속하는 Location 데이터 (페이징 처리된 결과)
//     */
//    Page<Location> findByRegion(Region region, Pageable pageable);

    /**
     * 모든 Location을 페이징처리하여 반환하는 메서드
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return 페이징처리된 모든 Location데이터를 반환
     */
    Page<Location> findAll(Pageable pageable);

    /**
     * 특정 위도, 경도 및 거리 내에 있는 장소들을 조회하고, 정렬 기준을 추가로 적용하는 메서드.
     *
     * @param latitude 조회할 기준 위도
     * @param longitude 조회할 기준 경도
     * @param distance 검색할 최대 거리 (단위: 킬로미터)
     * @param sort 결과 리스트에 적용할 정렬 기준
     * @return 주어진 범위 내의 장소들 (거리 기준 및 정렬 기준이 적용됨)
     */
    @Query("SELECT l FROM Location l WHERE " +
            "6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) * " +
            "COS(RADIANS(l.longitude) - RADIANS(:longitude)) + " +
            "SIN(RADIANS(:latitude)) * SIN(RADIANS(l.latitude))) <= :distance")
    List<Location> findLocationsWithinDistance(@Param("latitude") Double latitude,
                                               @Param("longitude") Double longitude,
                                               @Param("distance") Double distance,
                                               Sort sort);


    /**
     * 태그 이름으로 장소 목록을 조회
     *
     * @param tagName 필터링할 태그 이름
     */
    @Query("SELECT l FROM Location l " +
            "JOIN l.tags t " +
            "WHERE t.tagName = :tagName")
    List<Location> findLocationsByTagName(@Param("tagName") String tagName);
}


    // 만약에 위 메서드가 정상적으로 동작하면 아래 메서드도 실행해보자
    // 아래 메서드는 거리순으로 정렬된 list를 반환한다.
    // 정렬된 list에서 sort를 입력받아 추가적인 정렬 기준을 설정할 수 있다.
    // 또한 특정 location으로 부터 근처의 location이 얼마나 떨어져있는지도 계산한다.
//    @Query("SELECT new com.example.LocationWithDistanceDto(l, " +
//            "6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) * " +
//            "COS(RADIANS(l.longitude) - RADIANS(:longitude)) + " +
//            "SIN(RADIANS(:latitude)) * SIN(RADIANS(l.latitude)))) AS distance " +
//            "FROM Location l WHERE " +
//            "6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) * " +
//            "COS(RADIANS(l.longitude) - RADIANS(:longitude)) + " +
//            "SIN(RADIANS(:latitude)) * SIN(RADIANS(l.latitude))) <= :distance " +
//            "ORDER BY distance ASC")
//    List<LocationWithDistanceDto> findLocationsWithinDistance(@Param("latitude") Double latitude,
//                                                              @Param("longitude") Double longitude,
//                                                              @Param("distance") Double distance,
//                                                              Sort sort);




