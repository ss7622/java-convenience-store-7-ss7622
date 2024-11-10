package store.domain;

public class TotalPriceAndCount {

    private int totalPrice = 0;
    private int count = 0;

    public TotalPriceAndCount() {
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCount() {
        return count;
    }

    public void addTotalPrice(int Price) {
        this.totalPrice += Price;
    }

    public void minusTotalPrice(int Price) {
        this.totalPrice -= Price;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void minusCount(int count) {
        this.count -= count;
    }
}
