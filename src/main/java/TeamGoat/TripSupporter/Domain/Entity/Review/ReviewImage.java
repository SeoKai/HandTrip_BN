package TeamGoat.TripSupporter.Domain.Entity.Review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_review_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId; // 이미지 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "tbl_review_image_ibfk_1"))
    private Review review; // 리뷰 외래키

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 이미지 경로 또는 URL

    public ReviewImage(Review review, String imageUrl) {
        this.review = review;
        this.imageUrl = imageUrl;
    }
}
