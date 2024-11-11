package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    private static List<Promotion> promotions = new ArrayList<>();

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

    public static void initializePromotions() {
        promotions = new ArrayList<>();
    }

    public static Promotion checkPromotion(String promotionName) {
        if (promotionName.equals("null")) {
            return null;
        }
        for (Promotion promotion : promotions) {
            if (checkDuration(promotion, promotionName)) {
                return promotion;
            }
        }
        return null;
    }

    private static boolean checkDuration(Promotion promotion, String promotionName) {
        if (promotion.name.equals(promotionName)) {
            return (promotion.start_date.isEqual(DateTimes.now().toLocalDate())
                    || promotion.start_date.isBefore(DateTimes.now().toLocalDate()))
                    && (promotion.end_date.isEqual(DateTimes.now().toLocalDate())
                    || promotion.end_date.isAfter(DateTimes.now().toLocalDate()));
        }
        return false;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }
}
