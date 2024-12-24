package TeamGoat.TripSupporter.Mapper.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
import TeamGoat.TripSupporter.Domain.Entity.Planner.ToDo;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.stereotype.Component;

/**
 * ToDoMapper는 ToDo 엔티티와 ToDoDto 간의 변환을 담당합니다.
 */
@Component
public class ToDoMapper {

    /**
     * ToDo 엔티티를 ToDoDto로 변환합니다.
     *
     * @param toDo 변환할 ToDo 엔티티
     * @return 변환된 ToDoDto
     */
    public ToDoDto toDto(ToDo toDo) {
        return ToDoDto.builder()
                .toDoId(toDo.getToDoId()) // ToDo ID
                .locationId(toDo.getLocation().getLocationId()) // 장소 ID
                .locationName(toDo.getLocation().getLocationName()) // 장소 이름
                .formattedAddress(toDo.getLocation().getFormattedAddress()) // 주소
                .build();
    }

    /**
     * ToDoDto를 ToDo 엔티티로 변환합니다.
     *
     * @param toDoDto 변환할 ToDoDto
     * @param dailyPlan ToDo와 연결할 DailyPlan 엔티티
     * @return 변환된 ToDo 엔티티
     */
    public ToDo toEntity(ToDoDto toDoDto, DailyPlan dailyPlan) {
        Location location = Location.builder()
                .locationId(toDoDto.getLocationId()) // 장소 ID 설정
                .build(); // Location은 외부에서 조회 필요

        return ToDo.builder()
                .dailyPlan(dailyPlan) // 연결된 DailyPlan
                .location(location) // 연결된 Location
                .build();
    }
}
