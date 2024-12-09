package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.UserDto;

public interface UserService {
    void createUser(UserDto userDTO);
    UserDto loginUser(String email, String password);
    UserDto getUserProfile(Long id);
    void updateUser(UserDto userDTO);
}

