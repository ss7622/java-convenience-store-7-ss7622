package store.domain;

import store.domain.exception.BuyingErrorMessage;

public class BuyingInformation {

    private final String name;
    private int normalCount = 0;
    private int promotionCount = 0;

    public BuyingInformation(String name, int normalCount) {
        checkInventory(name, normalCount);
        this.name = name;
        this.normalCount = normalCount;
    }

    public void adjustCount(int normalCount, int promotionCount) {
        this.normalCount = normalCount;
        this.promotionCount = promotionCount;
    }

    public void addPromotionCount(int count) {
        this.promotionCount += count;
    }

    public String getName() {
        return name;
    }

    public int getNormalCount() {
        return normalCount;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public int getTotalCount() {
        return normalCount + promotionCount;
    }

    private void checkInventory(String name, int count) {
        if (!Products.isExist(name)) {
            throw new IllegalArgumentException(BuyingErrorMessage.IS_NOT_EXIST.getMessage());
        }
        if (Products.getAllQuantity(name) < count) {
            throw new IllegalArgumentException(BuyingErrorMessage.OVER_COUNT.getMessage());
        }
        if (count == 0) {
            throw new IllegalArgumentException(BuyingErrorMessage.IS_NOT_CORRECT_NUMBER.getMessage());
        }
    }
}
