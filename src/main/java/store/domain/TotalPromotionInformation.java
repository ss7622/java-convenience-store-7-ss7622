package store.domain;

import java.util.ArrayList;
import java.util.List;

// 증정 상품의 개수와 품목을 관리하는 클래스
public class TotalPromotionInformation {

    private final List<String> name = new ArrayList<>();
    private final List<Integer> price = new ArrayList<>();
    private final List<Integer> count = new ArrayList<>();

    public TotalPromotionInformation() {
    }

    public void addPromotionInformation(String name, int price, int count) {
        this.name.add(name);
        this.price.add(price);
        this.count.add(count);
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < price.size(); i++) {
            totalPrice += price.get(i) * count.get(i);
        }
        return totalPrice;
    }

    public List<String> getName() {
        return name;
    }

    public List<Integer> getCount() {
        return count;
    }


}
