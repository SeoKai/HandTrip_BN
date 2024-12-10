package TeamGoat.TripSupporter.Domain.Dto.Review;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@ToString
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class ReviewDto {
    private Long reviewId;

    private Long userId;

    private Long locationId;

    private double rating;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewCreatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewUpdatedAt;
}
