package TeamGoat.TripSupporter.Service.User.Util;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import TeamGoat.TripSupporter.Exception.User.UserMappingException;
import TeamGoat.TripSupporter.Exception.User.UserStatusException;

public class UserServiceValidator {

    public static void validateConvert(User user){
        if(user == null || user.getUserStatus() == null || user.getUserStatus().equals("")){
            throw new UserMappingException("해당 Dto를 엔티티로 변환하는대 실패했습니다.");
        }
    }
    public static void validateConvert(UserDto userDto){
        if(userDto == null ){
            throw new UserMappingException("해당 엔티티를 Dto로 변환하는대 실패했습니다.");
        }
    }

    /**
     * userStatus가 active가 아니면 예외 발생시킴
     * @param user
     */
    public static void UserStatusActiveCheck(User user){
        if(user.getUserStatus() != UserStatus.ACTIVE){
            throw new UserStatusException("비활성화 상태인 사용자");
        }
    }
    /**
     * String 입력받아 null이거나 isEmpty일때 예외를 발생시킴
     * @param keyword String값
     */
    public static void validateKeyword(String keyword) {
        if(keyword == null || keyword.isEmpty()){
            throw new IllegalArgumentException("문자열이 null이거나 비어있습니다.");
        }
    }
}
