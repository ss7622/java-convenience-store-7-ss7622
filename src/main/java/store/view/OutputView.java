package store.view;

import java.util.List;
import store.domain.BuyingInformation;
import store.domain.Products;
import store.domain.TotalPriceAndCount;
import store.domain.TotalPromotionInformation;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String PRODUCTS_GUIDE_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String START_OF_RECEIPT = "==============W 편의점================";
    private static final String START_OF_BUYING_INFORMATION = "상품명                수량       금액";
    private static final String START_OF_PROMOTION_INFORMATION = "=============증     정===============";
    private static final String START_OF_TOTAL_INFORMATION = "====================================";
    private static final String SEPARATOR = "-";
    private static final String NEW_LINE = "\n";

    public void printWelcomeMessage() {
        System.out.println(NEW_LINE + WELCOME_MESSAGE);
    }

    public void printProductsGuideMessage() {
        System.out.println(PRODUCTS_GUIDE_MESSAGE + NEW_LINE);
        List<Products> products = Products.getProducts();

        for (Products product : products) {
            System.out.println(SEPARATOR + product.toString());
        }
    }

    public void printBuyingInformationMessage(List<BuyingInformation> buyingInformation) {
        System.out.println(NEW_LINE + START_OF_RECEIPT);
        System.out.printf("%-16s\t%-4s\t  %-5s\n", "상품명", "수량", "금액");
        for (BuyingInformation information : buyingInformation) {
            System.out.printf("%-16s\t%-4d\t  %,-5d\n", information.getName(), information.getTotalCount(),
                    information.getTotalCount() * Products.getPrice(information.getName()));
        }

    }

    public void printPromotionInformation(TotalPromotionInformation totalPromotionInformation) {
        System.out.println(START_OF_PROMOTION_INFORMATION);
        List<String> name = totalPromotionInformation.getName();
        List<Integer> count = totalPromotionInformation.getCount();
        for (int i = 0; i < name.size(); i++) {
            if (count.get(i) != 0) {
                System.out.printf("%-16s\t%,-3d\n", name.get(i), count.get(i));
            }
        }
    }

    public void printTotalInformation(TotalPriceAndCount totalPriceAndCount,
                                      TotalPromotionInformation promotionInformation, int membershipSale) {
        System.out.println(START_OF_TOTAL_INFORMATION);
        int totalPrice = totalPriceAndCount.getTotalPrice();
        int promotionSale = promotionInformation.getTotalPrice();
        int finalPrice = totalPrice - promotionSale - membershipSale;
        System.out.printf("%-16s\t%-4d\t  %,-5d\n", "총구매액", totalPriceAndCount.getCount(), totalPrice);
        System.out.printf("%-24s\t  -%,-5d\n", "행사할인", promotionSale);
        System.out.printf("%-24s\t  -%,-5d\n", "멤버십할인", membershipSale);
        System.out.printf("%-24s\t  %,-5d\n", "내실돈", finalPrice);
    }
}
