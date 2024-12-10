package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@ToString
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
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
