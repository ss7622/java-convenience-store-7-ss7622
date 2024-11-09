package store.view.exception;

public enum InputFormatErrorMessage {

    IS_NOT_CORRECT_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    IS_NOT_CORRECT_ANSWER("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    InputFormatErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
