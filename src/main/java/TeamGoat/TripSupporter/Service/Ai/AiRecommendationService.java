package TeamGoat.TripSupporter.Service.Ai;

import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Entity.Ai.AiUser;
import TeamGoat.TripSupporter.Mapper.Ai.AiUserMapper;
import TeamGoat.TripSupporter.Repository.Ai.AiUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiUserRepository aiUserRepository;

//    /**
//     * 사용자 ID로 추천 데이터를 조회합니다.
//     *
//     * @param userId 사용자 ID
//     * @return 추천 데이터 리스트
//     */
//    public List<Long> getRecommendationsByUserId(Long userId) {
//        return aiUserRepository.findByUser_UserId(userId)
//                .stream()
//                .map(aiUser -> aiUser.getLocation().getLocationId()) // Location 객체에서 ID를 추출
//                .collect(Collectors.toList());
//    }

    /**
     * 새로운 추천 데이터를 저장합니다.
     *
     * @param aiUserDto 추천 데이터 DTO
     */
    public void saveRecommendation(AiUserDto aiUserDto) {
        AiUser aiUser = AiUserMapper.toEntity(aiUserDto);
        aiUserRepository.save(aiUser);
    }
}
