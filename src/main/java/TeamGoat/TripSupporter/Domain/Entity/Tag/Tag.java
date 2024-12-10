package TeamGoat.TripSupporter.Domain.Entity.Tag;

import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TBL_TAG")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long tagId;

    @Column(name = "TAG_NAME", nullable = false, unique = true, length = 255)
    private String tagName; // tag이름 중복불가능

    @OneToMany(mappedBy = "tag")  // 양방향 관계에서 반대편 엔티티(Planner)의 필드를 참조
    private Set<PlannerTag> planners = new HashSet<>();    // planner와 N:M관계
    // set을 사용하여 동일planner와 동일 tag쌍의 연결을 방지

    @Builder
    public Tag(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}