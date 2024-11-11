package store.domain;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.Application;
import store.domain.controller.ConvenienceStoreController;

class ProductsTest extends NsTest {

    private final ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();

    @BeforeEach
    void setUp() {
        convenienceStoreController.initialize();
    }

    @Test
    void 재고_생성_테스트() {
        Products.add("영양갱", 1000, 10, "null");

        Assertions.assertThat(Products.isExist("영양갱")).isEqualTo(true);
    }

    @Test
    void 재고_감소_테스트() {
        Products.reduceQuantity(3, "사이다", "탄산2+1");

        Assertions.assertThat(Products.getPromotionQuantity("사이다")).isEqualTo(5);
    }

    @Test
    void 없는_상품_입력_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[밤양갱-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 재고_수량_초과_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-21]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 잘못된_형식_예외_테스트() {
        assertSimpleTest(() -> {
            runException("(컵라면-12)", "N", "N");
            assertThat(output()).contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }


}