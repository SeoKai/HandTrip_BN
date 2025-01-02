package TeamGoat.TripSupporter.Service.Ai;

import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Entity.Ai.AiUser;
import TeamGoat.TripSupporter.Mapper.Ai.AiUserMapper;
import TeamGoat.TripSupporter.Repository.Ai.AiUserRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiUserRepository aiUserRepository;
    private final UserRepository userRepository; // UserRepository를 통해 userId를 조회

    /**
     * 이메일을 통해 사용자 ID를 조회합니다.
     *
     * @param email 사용자 이메일
     * @return 사용자 ID
     */
    public Long getUserIdByEmail(String email) {
        return userRepository.findUserIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자가 존재하지 않습니다: " + email));
    }


    public void saveRecommendations(List<AiUserDto> aiUserDtoList) {
        if (aiUserDtoList == null || aiUserDtoList.isEmpty()) {
            throw new IllegalArgumentException("추천 데이터가 비어있습니다.");
        }

        List<AiUser> aiUsers = aiUserDtoList.stream()
                .map(AiUserMapper::toEntity)
                .collect(Collectors.toList());

        aiUserRepository.saveAll(aiUsers); // 데이터베이스에 저장
    }

}


