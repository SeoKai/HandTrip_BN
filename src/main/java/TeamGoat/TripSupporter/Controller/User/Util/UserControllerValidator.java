package TeamGoat.TripSupporter.Controller.User.Util;

import TeamGoat.TripSupporter.Exception.User.IllegalEmailFormatException;

public class UserControllerValidator {

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
