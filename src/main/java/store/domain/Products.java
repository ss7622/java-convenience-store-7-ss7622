package store.domain;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.domain.exception.ProductsErrorMessage;

public class Products {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    private static List<Products> products = new ArrayList<>();

    private Products(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static void add(String name, int price, int quantity, String promotion) {
        validate(price, quantity);
        Products product = new Products(name, price, quantity, promotion);
        products.add(product);
    }

    public static List<Products> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public static void initializeProducts() {
        products = new ArrayList<>();
    }

    public static boolean isExist(String name) {
        for (Products product : products) {
            if (product.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void reduceQuantity(int quantity, String name, String promotion) {
        for (Products product : products) {
            if (product.name.equals(name) && product.promotion.equals(promotion)) {
                product.quantity -= quantity;
            }
        }
    }

    public static int getPrice(String name) {
        for (Products product : products) {
            if (product.name.equals(name)) {
                return product.price;
            }
        }
        return 0;
    }

    public static int getAllQuantity(String name) {
        int count = 0;
        for (Products product : products) {
            if (product.name.equals(name)) {
                count += product.quantity;
            }
        }
        return count;
    }

    public static int getNormalQuantity(String name) {
        int count = 0;
        for (Products product : products) {
            if (product.name.equals(name) && product.promotion.equals("null")) {
                count += product.quantity;
            }
        }
        return count;
    }

    public static int getPromotionQuantity(String name) {
        int count = 0;
        for (Products product : products) {
            if (product.name.equals(name) && !product.promotion.equals("null")) {
                count += product.quantity;
            }
        }
        return count;
    }

    public static String getPromotion(String name) {
        for (Products product : products) {
            if (product.name.equals(name) && !product.promotion.equals("null")) {
                return product.promotion;
            }
        }
        return "null";
    }

    public static boolean canClose() {
        int count = 0;
        for (Products product : products) {
            count += product.quantity;
        }
        return count == 0;
    }

    private static void validate(int price, int quantity) {
        if (price <= 0) {
            throw new IllegalArgumentException(ProductsErrorMessage.IS_NOT_POSITIVE.getMessage());
        }
        if (quantity < 0) {
            throw new IllegalArgumentException(ProductsErrorMessage.IS_NEGATIVE.getMessage());
        }
    }

    @Override
    public String toString() {
        String formatedPrice = NumberFormat.getInstance().format(price);
        String quantityString = quantity + "개";
        if (quantity == 0) {
            quantityString = "재고 없음";
        }
        if (promotion.equals("null")) {
            return String.format(" %s %s원 %s", name, formatedPrice, quantityString);
        }
        return String.format(" %s %s원 %s %s", name, formatedPrice, quantityString, promotion);
    }
}
