package store.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import store.domain.Products;
import store.domain.Promotion;
import store.service.exception.InitializerException;

public class Initializer {

    private static final String SEPARATOR = ",";

    public void products() throws IOException {
        Products.initializeProducts();
        Path path = Paths.get("src/main/resources/products.md");
        List<String> lines = Files.readAllLines(path);
        BufferedReader reader = new BufferedReader(new StringReader(String.join("\n", lines)));

        splitProductsFile(reader);
        reader.close();
    }

    public void promotions() throws IOException {
        Promotion.initializePromotions();
        Path path = Paths.get("src/main/resources/promotions.md");
        List<String> lines = Files.readAllLines(path);
        BufferedReader reader = new BufferedReader(new StringReader(String.join("\n", lines)));

        splitPromotionsFile(reader);
        reader.close();
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
        LocalDate startDate = LocalDate.parse(split[3]);
        LocalDate endDate = LocalDate.parse(split[4]);

        validatePromotionBuyGet(buy, get);
        validatePromotionDate(startDate, endDate);
        Promotion.addPromotion(name, buy, get, startDate, endDate);
    }

    private void validatePromotionBuyGet(int buy, int get) {
        if (buy <= 0 || get <= 0) {
            throw new IllegalArgumentException(InitializerException.IS_NOT_POSITIVE.getMessage());
        }
    }

    private void validatePromotionDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(InitializerException.IS_NOT_VALID_DATE.getMessage());
        }
    }

}
