package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 행사_할인_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-9],[사이다-6]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("콜라3사이다2");
        });
    }

    @Test
    void 프로모션_재고_부족_상황_테스트() {
        assertSimpleTest(() -> {
            run("[사이다-10]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("현재사이다4개는프로모션할인이적용되지않습니다.그래도구매하시겠습니까?(Y/N)");
        });
    }

    @Test
    void 일반_재고와_함께_구매할_때_행사_할인_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-14],[사이다-10]", "Y", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("콜라3사이다2");
        });
    }

    @Test
    void 프로모션_적용_가능_고객이_해당_수량만큼_가져오지_않았을_경우_테스트() {
        assertSimpleTest(() -> {
            run("[오렌지주스-1],[콜라-2]", "Y", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "현재오렌지주스은(는)1개를무료로더받을수있습니다.추가하시겠습니까?(Y/N)현재콜라은(는)1개를무료로더받을수있습니다.추가하시겠습니까?(Y/N)");
        });
    }

    @Test
    void 프로모션_재고_부족_안내_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-13]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "현재콜라4개는프로모션할인이적용되지않습니다.그래도구매하시겠습니까?(Y/N)");
        });
    }

    @Test
    void 일반_상품_멤버십_할인_테스트() {
        assertSimpleTest(() -> {
            run("[에너지바-1],[물-10]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-2,100");
        });
    }

    //프로모션 할인 적용이 되지 않은 상품에 대해 멤버십 할인을 적용해야 하므로, 콜라 3개, 사이다 2개에 대해 멤버십 할인을 적용한다.
    @Test
    void 행사_상품_멤버십_할인_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-12],[사이다-8]", "Y", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-1,500");
        });
    }

    @Test
    void 멤버십_할인_최대_한도_테스트() {
        assertSimpleTest(() -> {
            run("[정식도시락-8]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-8,000");
        });
    }

    @Test
    void 행사_상품_우선_재고_감소_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-3]", "N", "Y", "[콜라-3]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("-콜라1,000원7개탄산2+1");
        });
    }

    @Test
    void 행사_상품_계산_테스트() {
        assertSimpleTest(() -> {
            run("[오렌지주스-2],[콜라-3]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈3,800");
        });
    }

    @Test
    void 행사_상품과_일반_상품_멤버십_미적용_계산_테스트() {
        assertSimpleTest(() -> {
            run("[오렌지주스-2],[콜라-3],[에너지바-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈7,800");
        });
    }

    @Test
    void 행사_상품과_일반_상품_멤버십_적용_계산_테스트() {
        assertSimpleTest(() -> {
            run("[오렌지주스-2],[콜라-3],[에너지바-2]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈6,600");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
