package TeamGoat.TripSupporter.Service.User.Util;

public class UserControllerValidator {

    /**
     * .과 @이 포함되어있는지 여부를 확인하여 이메일 형식이 아니라면 예외를 발생시킵니다.
     * @param email 이메일
     * @return 포함하면 true, 안하면 false
     */
    public static boolean isValidEmail(String email) {
        if(!(email.contains("@") && email.contains("."))){
            throw new WrongEmailFormatException("이메일 형식이 아닙니다.");
        }

    }

}
