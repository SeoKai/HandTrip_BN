package TeamGoat.TripSupporter.Domain.Entity.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_planner")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "planner_id")
    private Long plannerId; // 고유 ID

    @Column(name = "planner_title", nullable = false, length = 100)
    private String plannerTitle; // 플래너 제목

    @Column(name = "planner_start_date", nullable = false)
    private LocalDate plannerStartDate; // 출발일

    @Column(name = "planner_end_date", nullable = false)
    private LocalDate plannerEndDate; // 도착일

    @Column(name = "email", nullable = false)
    private String email; // 사용자 이메일

    @Column(name = "planner_created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime plannerCreatedAt; // 생성 시간

    @Column(name = "planner_updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime plannerUpdatedAt; // 수정 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false) // 외래키 연결
    private Region region; // 연결된 지역(도시)

    @OneToMany(mappedBy = "planner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPlan> dailyPlans = new ArrayList<>();



    @Builder
    public Planner(String plannerTitle, LocalDate plannerStartDate, LocalDate plannerEndDate, String email, Region region) {
        this.plannerTitle = plannerTitle;
        this.plannerStartDate = plannerStartDate;
        this.plannerEndDate = plannerEndDate;
        this.email = email;
        this.region = region;
        this.plannerCreatedAt = LocalDateTime.now();
        this.plannerUpdatedAt = LocalDateTime.now();
    }

    public void updateWith(Planner updatedPlanner) {
        this.plannerTitle = updatedPlanner.getPlannerTitle();
        this.plannerStartDate = updatedPlanner.getPlannerStartDate();
        this.plannerEndDate = updatedPlanner.getPlannerEndDate();
        this.region = updatedPlanner.getRegion();
        // 기존 DailyPlans 제거
        this.dailyPlans.clear();

        // 새로운 DailyPlans 추가
        updatedPlanner.getDailyPlans().forEach(newDailyPlan -> {
            newDailyPlan.setPlanner(this); // Planner와 연결 설정
            this.dailyPlans.add(newDailyPlan);
        });

        this.plannerUpdatedAt = LocalDateTime.now();

    }

}
