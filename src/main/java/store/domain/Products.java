package store.domain;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    private static final List<Products> products = new ArrayList<>();

    private Products(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static void add(String name, int price, int quantity, String promotion) {

        Products product = new Products(name, price, quantity, promotion);
        products.add(product);
    }

    public static List<Products> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public String toString() {
        String formatedPrice = NumberFormat.getInstance().format(price);
        if (promotion.equals("null")) {
            return String.format(" %s %s %d개", name, formatedPrice, quantity);
        }
        return String.format(" %s %s %d개 %s", name, formatedPrice, quantity, promotion);
    }
}
