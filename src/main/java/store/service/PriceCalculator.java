package store.service;

import java.util.List;
import store.domain.BuyingInformation;
import store.domain.Products;

public class PriceCalculator {
    // 구매한 품목의 가격을 반환한다.
    public static int totalPrice(BuyingInformation buyingInformation) {
        int productPrice = Products.getPrice(buyingInformation.getName());
        return buyingInformation.getTotalCount() * productPrice;
    }

    // 구매한 품목 중 프로모션이 아닌 상품의 가격을 반환한다.
    public static int totalNormalPrice(List<BuyingInformation> buyingInformation) {
        int totalPrice = 0;
        for (BuyingInformation information : buyingInformation) {
            int normalCount = information.getNormalCount();
            int price = Products.getPrice(information.getName());

            totalPrice += price * normalCount;
        }
        return totalPrice;
    }
}