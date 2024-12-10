package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    private Long locationId;

    private String name;

    private String description;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime locationCreatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime locationUpdatedAt;

    private String imageUrl;

}
