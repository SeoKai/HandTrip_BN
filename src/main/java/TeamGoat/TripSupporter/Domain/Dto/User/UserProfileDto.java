package TeamGoat.TripSupporter.Domain.Dto.User;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Long userId;

    private String userNickname;

    private String profileImageUrl;

    private String userBio;

}
