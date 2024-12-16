package TeamGoat.TripSupporter.Domain.Entity.Review;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_review")
@Getter
@ToString
@Builder
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
    private Location locationId; // 위치 외래키

    @Column(name = "rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal rating; // 평점

    @Column(name = "comment")
    private String comment; // 리뷰 코멘트

    @Column(name = "review_created_at", updatable = false)
    private LocalDateTime reviewCreatedAt; // 리뷰 생성 시각

    @Column(name = "review_updated_at")
    private LocalDateTime reviewUpdatedAt; // 리뷰 수정 시각
}