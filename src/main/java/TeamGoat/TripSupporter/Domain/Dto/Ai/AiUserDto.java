package TeamGoat.TripSupporter.Domain.Dto.Ai;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AiUserDto {
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    @NotNull(message = "locationId는 필수입니다.")
    private Long locationId;

    @Min(1)
    @Max(5)
    private Integer rating;
}

