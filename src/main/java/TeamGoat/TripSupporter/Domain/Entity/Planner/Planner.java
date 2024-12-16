package TeamGoat.TripSupporter.Domain.Entity.Planner;

import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_planner")
@Getter
@Setter
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

    @Column(name = "planner_created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime plannerCreatedAt; // 생성 시간

    @Column(name = "planner_updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime plannerUpdatedAt; // 수정 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false) // 외래키 연결
    private Region region; // 연결된 지역(도시)

    @OneToMany(mappedBy = "planner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>(); // 포함된 일정 목록

    @Builder
    public Planner(String plannerTitle, LocalDate plannerStartDate, LocalDate plannerEndDate, Region region) {
        this.plannerTitle = plannerTitle;
        this.plannerStartDate = plannerStartDate;
        this.plannerEndDate = plannerEndDate;
        this.region = region;
        this.plannerCreatedAt = LocalDateTime.now();
        this.plannerUpdatedAt = LocalDateTime.now();
    }
}
