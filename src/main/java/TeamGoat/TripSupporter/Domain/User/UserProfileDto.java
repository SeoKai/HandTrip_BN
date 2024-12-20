package TeamGoat.TripSupporter.Domain.User;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserProfileDto {

    @NotBlank
    private Long userProfileId;

    @NotBlank(message="닉네임은 필수 입니다.")
    private String userNickname;

    private String profileImageUrl;

    private String userBio;

}
