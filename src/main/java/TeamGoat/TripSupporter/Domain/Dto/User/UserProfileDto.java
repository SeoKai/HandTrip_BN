package TeamGoat.TripSupporter.Domain.Dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserProfileDto {

    private Long userProfileId;
    private Long userId;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phoneNumber;

    private String profileImageUrl;

    private String userBio;

}
