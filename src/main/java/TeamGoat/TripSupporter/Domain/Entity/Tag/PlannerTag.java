package TeamGoat.TripSupporter.Domain.Entity.Tag;

import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_PLANNER_TAG")
@Getter
<<<<<<< HEAD
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class PlannerTag {   // planner - tag N:M관계를 위한 중계테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLANNER_TAG_ID")
    private Long plannerTagId;    // id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNER_ID", foreignKey = @ForeignKey(name = "FK_PLANNER_TAG_PLANNER"))
    private Planner planner;    // planner로부터 fk 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID", foreignKey = @ForeignKey(name = "FK_PLANNER_TAG_TAG"))
    private Tag tag;    // tag로부터 fk 받아옴
<<<<<<< HEAD

    @Builder
    public PlannerTag(Long plannerTagId, Planner planner, Tag tag) {
        this.plannerTagId = plannerTagId;
        this.planner = planner;
        this.tag = tag;
    }
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
}