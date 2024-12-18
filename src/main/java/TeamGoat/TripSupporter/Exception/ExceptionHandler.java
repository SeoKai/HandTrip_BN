package TeamGoat.TripSupporter.Exception;

// 예외 처리를 위한 인터페이스입니다.
public interface ExceptionHandler {

    /**
     * 예외 처리 메서드
     *
     * @param exception 처리할 예외 객체
     * @return 예외 처리 결과를 나타내는 객체 (예: 사용자에게 반환할 메시지, 상태 코드 등)
     */
    Object exceptionHandler(Exception exception);

}
