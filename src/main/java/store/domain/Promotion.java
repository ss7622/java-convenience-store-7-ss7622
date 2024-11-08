package store.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    private static final List<Promotion> promotions = new ArrayList<>();

    private Promotion(String name, int buy, int get, LocalDate start_date, LocalDate end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public static void addPromotion(String name, int buy, int get, LocalDate start_date, LocalDate end_date) {
        promotions.add(new Promotion(name, buy, get, start_date, end_date));
    }
}
