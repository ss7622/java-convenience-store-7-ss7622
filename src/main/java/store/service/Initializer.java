package store.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import store.Application;
import store.domain.Products;
import store.domain.Promotion;

public class Initializer {

    private static final String SEPARATOR = ",";

    public void products() throws IOException {
        Products.initializeProducts();
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("products.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        splitProductsFile(reader);
    }

    public void promotions() throws IOException {
        Promotion.initializePromotions();
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("promotions.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        splitPromotionsFile(reader);
    }

    private void splitProductsFile(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(SEPARATOR);
            makeProducts(split);
        }
    }

    private void makeProducts(String[] split) {
        String name = split[0];
        int price = Integer.parseInt(split[1]);
        int quantity = Integer.parseInt(split[2]);
        String promotion = split[3];

        Products.add(name, price, quantity, promotion);
    }

    private void splitPromotionsFile(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(SEPARATOR);
            makePromotions(split);
        }
    }

    private void makePromotions(String[] split) {
        String name = split[0];
        int buy = Integer.parseInt(split[1]);
        int get = Integer.parseInt(split[2]);
        LocalDate start_date = LocalDate.parse(split[3]);
        LocalDate end_date = LocalDate.parse(split[4]);

        Promotion.addPromotion(name, buy, get, start_date, end_date);
    }

}
