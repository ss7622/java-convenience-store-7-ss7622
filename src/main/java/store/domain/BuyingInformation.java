package store.domain;

import store.domain.exception.BuyingErrorMessage;

public class BuyingInformation {

    private final String name;
    private final int count;

    public BuyingInformation(String name, int count) {
        checkInventory(name, count);
        this.name = name;
        this.count = count;
    }

    private void checkInventory(String name, int count) {
        if (!Products.isExist(name)) {
            throw new IllegalArgumentException(BuyingErrorMessage.IS_NOT_EXIST.getMessage());
        }
        int quantity = Products.getQuantity(name);
        if (quantity < count) {
            throw new IllegalArgumentException(BuyingErrorMessage.OVER_COUNT.getMessage());
        }
    }
}
