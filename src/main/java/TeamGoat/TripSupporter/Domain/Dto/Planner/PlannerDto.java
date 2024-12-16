package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlannerDto {

    private Long plannerId;          // 플래너 ID

    private String plannerTitle;     // 플래너 제목

    private LocalDate plannerStartDate; // 출발일

    private LocalDate plannerEndDate;   // 도착일

    private Long regionId;           // 연결된 지역 ID

    private String regionName;       // 연결된 지역 이름

    private List<TodoDto> todos;     // 일정 목록
}
