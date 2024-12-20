package TeamGoat.TripSupporter.Domain.Dto.Bookmark;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookmarkPlannerDto {
    
    private Long bookmarkId;

    private Long plannerId;

    private Long userId;

}
