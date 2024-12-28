package TeamGoat.TripSupporter.Service.User.Util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class RandomStringGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz"; // 난수에 포함될 소문자
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 난수에 포함될 대문자
    private static final String DIGITS = "0123456789"; // 난수에 포함될 숫자들
    private static final String SPECIAL_CHARACTERS = "!@#$%";  //난수에 포함될 특수문자들

    public static SecureRandom random = new SecureRandom();

    /**
     * 입력받은 자릿수 길이 만큼의 난수 문자열을 특수문자의 갯수를 제한하여 생성합니다.
     * 생성된 난수는 !@#$%와 영어 대소문자, 숫자로 이루어져있습니다.
     * @param length 난수의 길이를 입력합니다
     * @param maxSpecialCharCount 생성될 난수의 특수문자 최대갯수를 제한합니다
     * @return 생성된 난수를 String으로 반환합니다.
     */
    public static String generateRandomString(int length, int maxSpecialCharCount) {

        StringBuilder randomString = new StringBuilder(length);
        // 특수문자 개수 제한
        int specialCount = Math.min(maxSpecialCharCount, length);  // 특수문자의 개수는 maxSpecialCharCount 못넘게

        // 특수문자 추가
        addRandomCharacters(randomString, SPECIAL_CHARACTERS, specialCount);

        // 나머지 길이는 대소문자 및 숫자로 채움
        int remainingLength = length - specialCount;
        if (remainingLength > 0) {
            String allCharacters = LOWERCASE + UPPERCASE + DIGITS;
            addRandomCharacters(randomString, allCharacters, remainingLength);
        }

        // 비밀번호를 섞기
        shuffleStringBuilder(randomString);

        return randomString.toString();
    }

    /**
     * 주어진 문자열에서 랜덤한 문자를 하나 골라 반환합니다.
     * @param characters 랜덤 문자를 선택할 문자 집합입니다.
     * @return 선택된 랜덤 문자를 반환합니다.
     */
    private static char getRandomCharacter(String characters) {
        int randomIndex = random.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }

    /**
     * 주어진 문자열에서 특정 문자 집합에 포함된 문자의 개수를 계산합니다.
     *
     * 예시 1) countCharacterType("홍길동", "홍")
     * - return : 1 ("홍"이 1번 포함됨)
     *
     * 예시 2) countCharacterType("111122233455", "13")
     * - return : 6 ("1"이 4번, "3"이 2번 포함됨)
     *
     * @param str 확인할 문자열
     * @param characterType 포함 여부를 확인할 문자 집합
     * @return 문자열에 포함된 문자 집합의 총 개수
     */
    private static int countCharacterType(String str, String characterType) {
        // characterType의 중복과 순서를 제거 하기위한 Set 선언
        Set<Character> characterSet = new HashSet<>();
        // characterType에서 글자를 하나씩 추출하여 set에 삽입
        for (char c : characterType.toCharArray()) {
            characterSet.add(c);
        }
        // count 초기화
        int count = 0;
        // str에서 characterType의 글자를 하나씩 추출
        for (char c : str.toCharArray()) {
            // 추출한 글자가 characterSet에 포함되어있는 글자와 같다면 count ++
            if (characterSet.contains(c)) {
                count++;
            }
        }
        return count;
    }
    /**
     * 주어진 StringBuilder에 랜덤한 문자를 추가하는 메서드입니다.
     * 이 메서드는 주어진 문자 집합에서 `count` 개수만큼 랜덤한 문자를 선택하여
     * `builder`에 추가합니다.
     * @param builder 랜덤 문자가 추가될 대상 문자열입니다.
     * @param characters 랜덤하게 선택할 수 있는 문자들이 들어있는 문자열입니다.
     * @param count 추가할 랜덤 문자의 개수입니다.
     */
    private static void addRandomCharacters(StringBuilder builder, String characters, int count) {
        for (int i = 0; i < count; i++) {
            builder.append(getRandomCharacter(characters));
        }
    }

    /**
     * 생성된 난수 문자열의 문자의 순서를 섞어 균등하게 분포되도록 만드는 메서드입니다.
     * 이 메서드는 난수 문자열을 무작위로 섞어서 예측 불가능한 문자열을 생성합니다.
     * @param builder 섞을 문자열 빌더입니다.
     */
    private static void shuffleStringBuilder(StringBuilder builder) {
        for (int i = 0; i < builder.length(); i++) {
            int randomIndex = random.nextInt(builder.length());
            char temp = builder.charAt(i);
            builder.setCharAt(i, builder.charAt(randomIndex));
            builder.setCharAt(randomIndex, temp);
        }
    }

}
