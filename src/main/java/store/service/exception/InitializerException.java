package store.service.exception;

public enum InitializerException {
    IS_NOT_POSITIVE("[ERROR] 프로모션 행사엔 양수만 입력될 수 있습니다."),
    IS_NOT_VALID_DATE("[ERROR] 올바른 행사 날짜 구성이 아닙니다.");

    private final String message;

    InitializerException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
