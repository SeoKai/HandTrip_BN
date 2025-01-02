package TeamGoat.TripSupporter.Repository.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationWithDistanceDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByRegionRegionId(Long regionId);

    /**
     * 모든 Location을 페이징처리하여 반환하는 메서드
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     *
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return 페이징처리된 모든 Location데이터를 반환
     */
    Page<Location> findAll(Pageable pageable);

    /**
     * locationName에 keyword가 포함된 Location 데이터를 페이징 처리하여 반환
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     *
     * @param keyword  검색 keyword
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return locationName에 keyword가 포함된 Location 데이터 (페이징 처리된 결과)
     */
    Page<Location> findByLocationNameContaining(String keyword, Pageable pageable);

    /**
     * 태그로 Location을 검색함 단일태그 검색도 가능 / 단 각 tag끼리는 And 연산
     * 즉 서로 다른 두 태그를 가진 Set을 입력하면
     * 태그 1과 태그 2를 모두 가진 Location이 반환됨
     *
     * @param tagNames 태그이름들이 들어있는 Set
     * @param tagCount TagNames에 들어간 tag의 종류 즉 tagNames.size()
     * @param pageable 페이징정보가 담긴 객체
     * @return
     */
    @Query("SELECT l FROM Location l " +
            "JOIN l.tags t " +
            "WHERE t.tagName IN :tagNames " +
            "GROUP BY l.id " +
            "HAVING COUNT(t.id) = :tagCount")
    Page<Location> findByTags(@Param("tagNames") Set<String> tagNames,
                              @Param("tagCount") long tagCount,
                              Pageable pageable);


    /**
     * 태그와 검색어로 Location을 검색함 단일태그 검색도 가능 / 단 각 tag끼리는 And 연산
     * LocationName에 검색어가 포함된 Location + 태그 검색
     * 즉 서로 다른 두 태그를 가진 Set을 입력하면
     * 태그 1과 태그 2를 모두 가진 Location이 반환됨
     * <p>
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     *
     * @param tagNames Set<String>으로 tagName을 받으므로 동적으로 값을 입력 받음
     * @param tagCount 태그 종류 tagNames.size()와 같음
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return tags필드에 List<>tagNames를 가지고 있는 Location 데이터 (페이징 처리된 결과)
     */
    @Query("SELECT l FROM Location l " +
            "JOIN l.tags t " +
            "WHERE t.tagName IN :tagNames " +
            "AND l.locationName LIKE %:keyword% " +
            "GROUP BY l.id " +
            "HAVING COUNT(t.id) = :tagCount")
    Page<Location> findByTagsAndKeyword(@Param("tagNames") Set<String> tagNames,
                                        @Param("tagCount") long tagCount,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);

    /**
     * 지역아이디를 기반으로 해당 지역에 속하는 모든 Location을 페이징처리하여 반환하는 메서드
     *
     * @param regionId 지역아이디
     * @param pageable 페이징정보가 담긴 객체
     * @return
     */
    Page<Location> findByRegion_RegionId(Long regionId, Pageable pageable);

    /**
     * regionName에 해당하는 Region을 기준으로 Location 데이터를 페이징 처리하여 반환
     * Pageable객체 작성 예시 PageRequest.of(0, 10);  // 첫 번째 페이지, 10개 항목
     *
     * @param regionId 검색할 Region의 Id
     * @param keyword
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return regionName에 해당하는 Region에 속하는 Location 데이터 (페이징 처리된 결과)
     */
    Page<Location> findByRegion_RegionIdAndLocationNameContaining(Long regionId, String keyword, Pageable pageable);


    /**
     * 태그로 Location을 검색함 단일태그 검색도 가능 / 단 각 tag끼리는 And 연산
     * 즉 서로 다른 두 태그를 가진 Set을 입력하면
     * 태그 1과 태그 2를 모두 가진 Location이 반환됨
     *
     * @param tagNames 태그이름이 들어있는 Set
     * @param tagCount tagNames에 들어있는 태그 종류 tagNames.size()와 같음
     * @param regionId 여행지가 속한 지역Id
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return
     */
    @Query("SELECT l FROM Location l " +
            "LEFT JOIN l.tags t " +
            "WHERE l.region.regionId = :regionId " +
            "AND t.tagName IN :tagNames " +
            "GROUP BY l.id " +
            "HAVING COUNT(t.id) = :tagCount")
    Page<Location> findByTagsAndRegion(
            @Param("tagNames") Set<String> tagNames,
            @Param("tagCount") long tagCount,
            @Param("regionId") Long regionId,
            Pageable pageable);


    /**
     * 태그로 Location을 검색함 단일태그 검색도 가능 / 단 각 tag끼리는 And 연산
     * 즉 서로 다른 두 태그를 가진 Set을 입력하면
     * 태그 1과 태그 2를 모두 가진 Location이 반환됨
     *
     * @param tagNames 태그이름이 들어있는 Set
     * @param tagCount tagNames에 들어있는 태그 종류 tagNames.size()와 같음
     * @param regionId 여행지가 속한 지역Id
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기)
     * @return
     */
    @Query("SELECT l FROM Location l " +
            "LEFT JOIN l.tags t " +
            "WHERE l.region.regionId = :regionId " +
            "AND t.tagName IN :tagNames " +
            "AND l.locationName LIKE %:keyword% " +
            "GROUP BY l.id " +
            "HAVING COUNT(t.id) = :tagCount")
    Page<Location> findByTagsAndRegionAndKeyword(
            @Param("tagNames") Set<String> tagNames,
            @Param("tagCount") long tagCount,
            @Param("regionId") Long regionId,
            @Param("keyword") String keyword,
            Pageable pageable);


    /**
     * 거리순으로 정렬된 list를 반환한다. 정렬된 list에서 sort를 입력받아 추가적인 정렬 기준을 설정할 수 있다.
     * 또한 특정 location으로 부터 근처의 location이 얼마나 떨어져있는지도 계산한다.
     *
     * @param latitude  중심위도
     * @param longitude 중심경도
     * @param distance  거리(단위 : km)
     * @param sort      정렬 기준
     * @return 메서드는 거리순으로 정렬된 list를 반환한다.
     */
    @Query("SELECT new TeamGoat.TripSupporter.Domain.Dto.Location.LocationWithDistanceDto" +
            "(l, 6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) * COS(RADIANS(l.longitude)" +
            " - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(l.latitude))) AS distance)" +
            " FROM Location l WHERE 6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) *" +
            " COS(RADIANS(l.longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(l.latitude)))" +
            " <= :distance ORDER BY distance ASC")
    List<LocationWithDistanceDto> findLocationsWithinDistance(@Param("latitude") Double latitude,
                                                              @Param("longitude") Double longitude,
                                                              @Param("distance") Double distance,
                                                              Sort sort);


    /**
     * @return 음식 태그가 제외된 관광지 정보들을 랜덤하게 반환
     */
    @Query(value = """
                SELECT DISTINCT l.* 
                FROM tbl_location l
                JOIN tbl_location_tag lt ON l.location_id = lt.location_id
                JOIN tbl_tag t ON lt.tag_id = t.tag_id
                WHERE t.tag_name IN ('관광명소', '랜드마크', '문화', '쇼핑')
                  AND l.location_id NOT IN (
                      SELECT lt.location_id 
                      FROM tbl_location_tag lt
                      JOIN tbl_tag t ON lt.tag_id = t.tag_id
                      WHERE t.tag_name = '음식'
                  )
                ORDER BY RAND()
                LIMIT 5
            """, nativeQuery = true)
    List<Location> findRandomPlacesExcludingFood();


    /**
     * 장소 이름으로 Location 엔티티를 조회합니다.
     *
     * @param locationName 조회할 장소 이름
     * @return Location 엔티티(Optional)
     */
    Optional<Location> findByLocationName(String locationName);

    @Query(value = """
                SELECT DISTINCT l.* 
                FROM tbl_location l
                JOIN tbl_location_tag lt ON l.location_id = lt.location_id
                JOIN tbl_tag t ON lt.tag_id = t.tag_id
                WHERE t.tag_name IN ('관광명소', '랜드마크', '문화', '쇼핑')
                  AND l.location_id NOT IN (
                      SELECT lt.location_id 
                      FROM tbl_location_tag lt
                      JOIN tbl_tag t ON lt.tag_id = t.tag_id
                      WHERE t.tag_name = '음식'
                  )
                ORDER BY RAND()
                LIMIT 4
            """, nativeQuery = true)
    List<Location> findRandomTopLocationsExcludingFood();
}




