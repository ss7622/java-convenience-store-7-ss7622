package store.service;

import store.domain.BuyingInformation;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.TotalPromotionInformation;

public class PromotionManager {
    // 프로모션 기간 중인지 확인 후, 프로모션 적용이 안 되는 상품의 개수를 반환
    public int cantApplyPromotionCount(BuyingInformation buyingInformation) {
        Promotion promotion = checkPromotion(buyingInformation);
        if (promotion == null) {
            return -1;
        }
        return buyingInformation.getPromotionCount() % (promotion.getBuy() + promotion.getGet());
    }

    // 구매한 상품이 프로모션 기간 중에 있다면, 프로모션 재고와 일반 재고로 갯수를 나누는 작업
    public void divideNormalAndPromotionProduct(BuyingInformation buyingInformation) {
        if (checkPromotion(buyingInformation) == null) {
            return;
        }
        int totalCount = buyingInformation.getNormalCount();
        int promotionQuantity = Products.getPromotionQuantity(buyingInformation.getName());
        if (totalCount < promotionQuantity) {
            buyingInformation.adjustCount(0, totalCount);
            return;
        }
        buyingInformation.adjustCount(totalCount - promotionQuantity, promotionQuantity);
    }

    // 무료로 더 받을 수 있는 프로모션 상품의 개수를 체크하는 메서드
    public int canGetFreeCount(BuyingInformation buyingInformation) {
        Promotion promotion = checkPromotion(buyingInformation);
        if (promotion == null) {
            return 0;
        }
        if (checkCanGive(buyingInformation, promotion)) {
            return promotion.getGet();
        }
        return 0;
    }

    // 구매한 상품 중 무료로 받을 수 있는 상품을 TotalPromotionInformation 객체에 포함한다.
    public void addPromotionProduct(TotalPromotionInformation totalPromotionInformation,
                                    BuyingInformation buyingInformation) {
        Promotion promotion = checkPromotion(buyingInformation);
        if (promotion == null) {
            return;
        }
        String name = buyingInformation.getName();
        int count = buyingInformation.getPromotionCount() / (promotion.getBuy() + promotion.getGet());
        count *= promotion.getGet();
        totalPromotionInformation.addPromotionInformation(name, Products.getPrice(name), count);
    }

    private Promotion checkPromotion(BuyingInformation buyingInformation) {
        String promotionName = Products.getPromotion(buyingInformation.getName());
        return Promotion.checkPromotion(promotionName);
    }

    // 상품을 추가로 더 줄 수 있는지 재고를 확인하는 메서드
    private boolean checkCanGive(BuyingInformation buyingInformation, Promotion promotion) {
        int remain = buyingInformation.getTotalCount() % (promotion.getGet() + promotion.getBuy());
        int totalGiveCount = buyingInformation.getPromotionCount() + promotion.getGet();

        return remain == promotion.getBuy();
    }
//    &&totalGiveCount <= Products.getPromotionQuantity(buyingInformation.getName()
}
