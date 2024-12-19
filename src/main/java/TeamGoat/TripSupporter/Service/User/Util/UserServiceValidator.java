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

    /**
     * 휴대폰 번호 형식의 문자열인지 확인하는 메서드
     * 정규식을 통해 010+8자리숫자 인지 확인
     * @param phoneNumber null값 입력받으면 NullPointerException 예외 발생
     * @return 휴대폰번호 형식이면 true, 아니면 false
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        String phoneRegex = "^010[0-9]{8}$";  // 010 뒤에 8자리 숫자
        return phoneNumber.matches(phoneRegex);
    }

    /**
     * 이메일 형식의 문자열인지 확인하는 메서드
     * 정규식을 통해 이메일형식인지 확인
     * @param email null값 입력받으면 NullPointerException 예외 발생
     * @return email형식이면 true, 아니면 false
     */
    public static boolean isEmailAddress(String email) {
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        return email.matches(emailRegex);
    }
}
