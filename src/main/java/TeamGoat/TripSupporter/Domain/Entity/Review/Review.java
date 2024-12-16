package TeamGoat.TripSupporter.Domain.Entity.Review;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tbl_review")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId; // 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "tbl_review_ibfk_1"))
    private User user; // 사용자 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "tbl_review_ibfk_2"))

    private Location location; // 위치 외래키

    @Column(name = "rating", nullable = false)
    private Integer rating; // 평점

    @Column(name = "comment")
    private String comment; // 리뷰 코멘트

    @Column(name = "PLANNER_CREATED_AT", updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime reviewCreatedAt; // 리뷰 생성 시각

    @Column(name = "PLANNER_UPDATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime reviewUpdatedAt; // 리뷰 수정 시각

    @Enumerated(EnumType.STRING)
    @Column(name = "REVIEW_STATUS", nullable = false)
    private ReviewStatus reviewStatus = ReviewStatus.ACTIVE;  //enum : ACTIVE, DELETED, PENDING(임시저장)

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images = new ArrayList<>();

    @Builder
    public Review(Long reviewId, User user, Location location, Integer rating, String comment, LocalDateTime reviewCreatedAt, LocalDateTime reviewUpdatedAt,ReviewStatus reviewStatus) {
        this.reviewId = reviewId;
        this.user = user;
        this.location = location;
        this.rating = rating;
        this.comment = comment;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewUpdatedAt = reviewUpdatedAt;
        this.reviewStatus = Objects.requireNonNullElse(reviewStatus, ReviewStatus.ACTIVE);
    }


    /**
     * Entity무결성을 지키기 위해 Entity내부에 자기자신의 값을 변경하는 메서드 작성
     * @param rating    별점 (1~5)
     * @param comment   리뷰본문
     */
    public void updateReview(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.reviewUpdatedAt = LocalDateTime.now(); // 수정시각 갱신
    }

    // 상태 확인 메서드들
    public boolean isActive() {
        return this.reviewStatus == ReviewStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.reviewStatus == ReviewStatus.DELETED;
    }

    public boolean isPending() {
        return this.reviewStatus == ReviewStatus.PENDING;
    }

    // 이미지 추가 메서드
    public void addImage(String imageUrl) {
        if (images.size() >= 3) {
            throw new IllegalStateException("리뷰에는 최대 3개의 이미지만 추가할 수 있습니다.");
        }
        this.images.add(new ReviewImage(this, imageUrl));
    }

    // 이미지 삭제 메서드
    public void removeImage(String imageUrl) {
        images.removeIf(image -> image.getImageUrl().equals(imageUrl));
    }

}