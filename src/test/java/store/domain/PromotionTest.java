package store.domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.controller.ConvenienceStoreController;

class PromotionTest {

    private final ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();

    @BeforeEach
    void setUp() {
        convenienceStoreController.initialize();
    }

    @Test
    void 행사_기간_중일_때_판단_하는_테스트() {
        Promotion.addPromotion("테스트", 5, 1,
                LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30"));

        Assertions.assertThat(Promotion.checkPromotion("테스트").getBuy()).isEqualTo(5);
    }

    @Test
    void 행사_기간이_아닐_때_판단_하는_테스트() {
        Promotion.addPromotion("테스트2", 5, 1,
                LocalDate.parse("2024-10-01"), LocalDate.parse("2024-10-30"));

        Assertions.assertThat(Promotion.checkPromotion("테스트2")).isEqualTo(null);
    }
}
