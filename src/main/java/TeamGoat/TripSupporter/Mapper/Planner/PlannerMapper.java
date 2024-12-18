//package TeamGoat.TripSupporter.Mapper;
//
//import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
//import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
//import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
//import TeamGoat.TripSupporter.Domain.Entity.Planner.ToDo;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class PlannerMapper {
//
//    // Planner → PlannerDto
//    public PlannerDto toPlannerDto(Planner planner) {
//        return PlannerDto.builder()
//                .plannerId(planner.getPlannerId())
//                .plannerTitle(planner.getPlannerTitle())
//                .plannerStartDate(planner.getPlannerStartDate())
//                .plannerEndDate(planner.getPlannerEndDate())
//                .regionName(planner.getRegion().getRegionName()) // 지역 이름
//                .dailyPlans(toDailyPlanDtoList(planner.getDailyPlans())) // 하루 일정 목록 변환
//                .build();
//    }
//
//    // PlannerDto → Planner (Entity 변환)
//    public Planner toEntity(PlannerDto plannerDto, Region region, String email) {
//        return Planner.builder()
//                .plannerTitle(plannerDto.getPlannerTitle())
//                .plannerStartDate(plannerDto.getPlannerStartDate())
//                .plannerEndDate(plannerDto.getPlannerEndDate())
//                .region(region) // Region 엔티티 설정
//                .email(email)   // 사용자 이메일 설정
//                .build();
//    }
//
//    // DailyPlan → DailyPlanDto
//    public DailyPlanDto toDailyPlanDto(DailyPlan dailyPlan) {
//        return DailyPlanDto.builder()
//                .dailyPlanId(dailyPlan.getDailyPlanId())
//                .planDate(dailyPlan.getPlanDate())
//                .toDos(toToDoDtoList(dailyPlan.getToDos())) // ToDo 목록 변환
//                .build();
//    }
//
//    // ToDo → ToDoDto
//    public ToDoDto toToDoDto(ToDo toDo) {
//        return ToDoDto.builder()
//                .toDoId(toDo.getToDoId())
//                .locationId(toDo.getLocation().getLocationId())
//                .locationName(toDo.getLocation().getLocationName())
//                .formattedAddress(toDo.getLocation().getFormattedAddress())
//                .latitude(toDo.getLocation().getLatitude())
//                .longitude(toDo.getLocation().getLongitude())
//                .build();
//    }
//
//    // DailyPlan List → DailyPlanDto List
//    private List<DailyPlanDto> toDailyPlanDtoList(List<DailyPlan> dailyPlans) {
//        return dailyPlans.stream()
//                .map(this::toDailyPlanDto)
//                .collect(Collectors.toList());
//    }
//
//    // ToDo List → ToDoDto List
//    private List<ToDoDto> toToDoDtoList(List<ToDo> toDos) {
//        return toDos.stream()
//                .map(this::toToDoDto)
//                .collect(Collectors.toList());
//    }
//}
