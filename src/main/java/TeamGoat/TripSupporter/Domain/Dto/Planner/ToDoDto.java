package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ToDoDto {
    private Long toDoId;         // ToDo ID
    private Long locationId;     // 장소 ID
    private String locationName; // 장소 이름
    private String formattedAddress; // 장소 주소
    private Double latitude;     // 위도
    private Double longitude;    // 경도
    private String placeImgUrl; // 장소 이미지 URL (새로 추가)
}

