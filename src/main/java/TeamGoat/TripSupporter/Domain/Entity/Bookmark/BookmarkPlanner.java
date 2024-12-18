package TeamGoat.TripSupporter.Domain.Entity.Bookmark;

import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_BOOKMARK")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkPlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKMARK_ID")
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER_BOOKMARK"))
    private User user;  // 북마크한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNER_ID", foreignKey = @ForeignKey(name = "FK_PLANNER_BOOKMARK"))
    private Planner planner;  // 북마크한 플래너


    @Builder
    public BookmarkPlanner(Long bookmarkId, User user, Planner planner) {
        this.bookmarkId = bookmarkId;
        this.user = user;
        this.planner = planner;
    }


}