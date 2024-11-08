package store.domain.exception;

public enum BuyingErrorMessage {

    IS_NOT_EXIST("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_COUNT("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String message;

    BuyingErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
