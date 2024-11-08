package store.view;

import java.util.List;
import store.domain.Products;

public class OutputView {

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String PRODUCTS_GUIDE_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String SEPARATOR = "-";
    private static final String NEW_LINE = "\n";

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printProductsGuideMessage() {
        System.out.println(PRODUCTS_GUIDE_MESSAGE + NEW_LINE);
        List<Products> products = Products.getProducts();

        for (Products product : products) {
            System.out.println(SEPARATOR + product.toString());
        }
    }
}
