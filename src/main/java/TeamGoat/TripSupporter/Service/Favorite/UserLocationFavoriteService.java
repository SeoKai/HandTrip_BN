package TeamGoat.TripSupporter.Service.Favorite;

import TeamGoat.TripSupporter.Domain.Dto.Favorite.UserFavoriteLocationsDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Favorite.UserLocationFavorite;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Exception.Favorite.DuplicateFavoriteException;
import TeamGoat.TripSupporter.Exception.Favorite.FavoriteNotFoundException;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Favorite.UserLocationFavoriteRepository;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import TeamGoat.TripSupporter.Service.Review.Util.ReviewServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLocationFavoriteService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserLocationFavoriteRepository favoriteRepository;
    private final LocationMapper locationMapper;


    @Transactional
    public void addFavoriteLocation(String userEmail, Long locationId) {

        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException("여행지를 찾을 수 없습니다."));

        if (favoriteRepository.existsByUserAndLocation(user, location)) {
            throw new DuplicateFavoriteException("이미 즐겨찾기에 등록된 장소입니다.");
        }

        UserLocationFavorite favorite = UserLocationFavorite.builder()
                .user(user)
                .location(location)
                .build();

        favoriteRepository.save(favorite);

    }

    @Transactional
    public void deleteFavoriteLocation(String userEmail, Long locationId){
        // 사용자 조회
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 여행지 조회
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("여행지를 찾을 수 없습니다."));

        // 사용자와 여행지에 해당하는 즐겨찾기 엔티티 조회
        UserLocationFavorite favorite = favoriteRepository.findByUserAndLocation(user, location)
                .orElseThrow(() -> new FavoriteNotFoundException("해당 즐겨찾기를 찾을 수 없습니다."));

        favoriteRepository.delete(favorite);
    }

    /**
     * 주어진 사용자 이메일에 대해 즐겨찾기한 여행지 목록을 페이징 처리하여 반환
     *
     * @param userEmail 사용자의 이메일
     * @param page 페이징을 위한 페이지 번호 (0부터 시작)
     * @param pageSize 한 페이지에 포함될 항목 수
     * @param sortValue 정렬 기준 필드
     * @param sortDirection 정렬 방향 ("ASC" 또는 "DESC")
     * @return 페이징 처리된 사용자 즐겨찾기 여행지 목록 (LocationResponseDto 리   스트)
     *
     * @throws UserNotFoundException 사용자가 존재하지 않으면 발생
     * @throws LocationNotFoundException 여행지 정보가 존재하지 않으면 발생
     */
    @Transactional
    public Page<LocationResponseDto> getUserFavoriteLocations(String userEmail,int page,int pageSize, String sortValue, String sortDirection){
        // 사용자 조회
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        Long UserId = user.getUserId();
        log.info("1, userId : {}, userEmail : {}",UserId,userEmail);
        // 페이징 처리와 정렬을 위한 Pageable 객체를 생성
        Pageable pageable = getPageable(page,pageSize,sortValue,sortDirection);

        // 사용자의 여행지 즐겨찾기 목록 조회
        Page<UserLocationFavorite> favorites = favoriteRepository.findByUser(user,pageable);

        // 즐겨찾기 목록을 LocationResponseDto로 변환하여 반환
        return favorites.map(favorite -> {
            Location location = favorite.getLocation();
            return locationMapper.toResponseDto(location);  // LocationResponseDto로 변환
        });


    }

    @Transactional
    public Long getFavoriteUserCountForLocation(Long locationId) {
        // 특정 여행지를 즐겨찾기한 사용자 수를 직접 DB에서 계산
        return favoriteRepository.countByLocation_locationId(locationId);
    }


    /**
     * 정렬기준에 따라 Pageable 객체 반환 (기본 정렬: ReviewCreatedAt DESC)
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating)
     * @param sortDirection : 정렬 방향 (asc, desc)
     * @return : Pageable 객체
     */
    private Pageable getPageable(int page, int pageSize, String sortValue, String sortDirection) {
        // 정렬기준, 정렬 방향을 설정하지 않았을 경우 default 정렬 기준 : 최신순
        if (sortValue == null || sortValue.isEmpty()) {
            sortValue = "createdAt";  // 기본 정렬 기준: CreatedAt
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc";  // 기본 정렬 방향: 내림차순
        }

        // 정렬 기준을 sortValue로 설정
        Sort sort = Sort.by(sortValue);
        // 정렬 방향 확인
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        log.info("현재 Page : " + page + ",정렬 기준 : " + sortValue + ",정렬 방향 : " +sortDirection);

        Pageable pageable = PageRequest.of(page,pageSize,sort);
        ReviewServiceValidator.validatePageable(pageable);

        return pageable;
    }
}
