package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // findAll , 정렬기준 - CreateAt 내림차순(최신순), 페이징처리
    Page<Review> findAllByOrderByReviewCreatedAtDesc(Pageable pageable);

    // LoactionId를 기준으로 검색, 정렬 기준 - CreateAt 내림차순(최신순), 페이징처리
    Page<Review> findByLocation_LocationId(Long locationId, Pageable pageable);

    // UserId를 기준으로 검색, 정렬 기준 - CreateAt 내림차순(최신순), 페이징처리
    Page<Review> findByUser_UserId(Long userId, Pageable pageable);

//    // comment에 특정 키워드가 포함된 리뷰 검색 , CreateAt 내림차순(최신순), 페이징처리
//    @Query("SELECT r FROM Review r WHERE r.comment LIKE %:keyword% ORDER BY r.reviewCreatedAt DESC")
//    Page<Review> findByCommentContainingOrderByReviewCreatedAtDesc(@Param("keyword") String keyword, Pageable pageable);


}
