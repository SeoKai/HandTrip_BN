package TeamGoat.TripSupporter.Repository.Review;

import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 기본적인 CRUD는 JpaRepository에서 제공해주는 기본 메서드 사용


    /**
     * locationId를 기준으로 검색, 페이징처리
     * @param locationId : 여행지아이디
     * @param pageable : pageable 객체 작성예시
     * Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
     * page : 시작페이지 (보통 0) , size : 한페이지에 표시될 데이터 수, Sort.by : 정렬조건
     * @return : 페이징된 Review 엔티티
     */
    Page<Review> findByLocation_LocationIdAndReviewStatus(Long locationId,ReviewStatus reviewStatus,Pageable pageable);

    /**
     * locationId를 기준으로 검색, 페이징 처리 X
     * @param locationId : 여행지아이디
     * @return : optional<List<Review>>>로 반환
     */
    Optional<List<Review>> findByLocation_LocationId(Long locationId);

    /**
     * UserId를 기준으로 검색, 페이징처리
     * @param userId : 유저아이디
     * @param pageable : pageable 객체 작성예시
     * Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
     * page : 시작페이지 (보통 0) , size : 한페이지에 표시될 데이터 수, Sort.by : 정렬조건
     * @return : 페이징된 Review 엔티티
     */
    Page<Review> findByUser_UserIdAndReviewStatus(Long userId,ReviewStatus reviewStatus,Pageable pageable);

//    // comment에 특정 키워드가 포함된 리뷰 검색 , CreateAt 내림차순(최신순), 페이징처리
//    Page<Review> findByCommentContaining(String keyword, Pageable pageable);


    /**
     * Status 상태에 따라 조회하는 메서드
     * @param reviewStatus review상태 (ACTIVE, DELETED, PENDING)
     * @param pageable pageable 객체 작성예시
     * Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
     * page : 시작페이지 (보통 0) , size : 한페이지에 표시될 데이터 수, Sort.by : 정렬조건
     * @return 페이징된 Review 엔티티
     */
    Page<Review> findByReviewStatus(ReviewStatus reviewStatus, Pageable pageable);


    Optional<Review> findByReviewIdAndReviewStatus(Long reviewId, ReviewStatus reviewStatus);


}
