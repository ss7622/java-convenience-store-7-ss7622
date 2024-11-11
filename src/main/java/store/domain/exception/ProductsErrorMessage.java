package store.domain.exception;

public enum ProductsErrorMessage {
    IS_NOT_POSITIVE("[ERROR] 제품 가격엔 양수만 입력될 수 있습니다."),
    IS_NEGATIVE("[ERROR] 재고값에 음수는 입력될 수 없습니다.");

    private final String message;

    ProductsErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
