package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.BuyingInformation;
import store.view.exception.InputFormatErrorMessage;

public class InputView {

    private static final String BUYING_GUIDE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_GUIDE_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String RETRY_GUIDE_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";
    private static final String BUYING_INFORMATION_PATTERN = "^\\[([가-힣|a-z]+)-([0-9]+)\\]$";

    public List<BuyingInformation> inputBuyingInformation() {
        System.out.println(NEW_LINE + BUYING_GUIDE_MESSAGE);
        return validateFormat(Console.readLine());
    }

    public String inputGetAdditionalProduct(String name, int count) {
        System.out.println(NEW_LINE + "현재 " + name + "은(는) " + count + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        while (true) {
            try {
                return validateAnswer(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String inputNonPromotion(String name, int count) {
        System.out.println(NEW_LINE + "현재 " + name + " " + count + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        while (true) {
            try {
                return validateAnswer(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String inputMembership() {
        System.out.println(NEW_LINE + MEMBERSHIP_GUIDE_MESSAGE);
        while (true) {
            try {
                return validateAnswer(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String inputRetry() {
        System.out.println(NEW_LINE + RETRY_GUIDE_MESSAGE);
        while (true) {
            try {
                return validateAnswer(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<BuyingInformation> validateFormat(String input) {
        String[] allInformation = input.split(SEPARATOR);
        List<BuyingInformation> buyingInformation = new ArrayList<>();
        for (String eachInformation : allInformation) {
            buyingInformation.add(extractBuyingInformation(eachInformation));
        }
        return buyingInformation;
    }

    private BuyingInformation extractBuyingInformation(String input) {
        Pattern pattern = Pattern.compile(BUYING_INFORMATION_PATTERN);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException(InputFormatErrorMessage.IS_NOT_CORRECT_FORMAT.getMessage());
        }

        return new BuyingInformation(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    private String validateAnswer(String answer) {
        if (!(answer.equals("Y") || answer.equals("N"))) {
            throw new IllegalArgumentException(InputFormatErrorMessage.IS_NOT_CORRECT_ANSWER.getMessage());
        }
        return answer;
    }
}
