package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Entity.Favorite.UserLocationFavorite;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Repository.Favorite.UserLocationFavoriteRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserLocationFavoriteRepositoryTest {

    @Autowired
    private UserLocationFavoriteRepository userLocationFavoriteRepository;

    @Autowired
    private UserRepository userRepository; // User 엔티티도 사용해야 하므로 UserRepository를 주입받아야 합니다.

    @Test
    public void testFindByUser() {
        // 특정 사용자를 조회
        User user = userRepository.findById(37L).orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 사용자의 즐겨찾기를 조회
        List<UserLocationFavorite> favorites = userLocationFavoriteRepository.findByUser(user);

        // 결과가 null이 아니고, 빈 리스트가 아닌지 확인
        assertNotNull(favorites);
        assertFalse(favorites.isEmpty());
//        System.out.println(favorites);
    }
}