package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_tag")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId; // 고유 ID

    @Column(name = "tag_name", nullable = false, unique = true)
    private String tagName; // 태그 이름

    @Builder
    public Tag(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
