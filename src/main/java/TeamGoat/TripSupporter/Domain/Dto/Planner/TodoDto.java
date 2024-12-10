package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TodoDto {
    private Long todoId;

    private String DailyPlanId;

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private LocalTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private LocalTime endTime;

    private String description;

    private String locationId;

}
