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
    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";
    private static final String BUYING_INFORMATION_PATTERN = "\\[([가-힣]+)-([0-9]+)\\]";

    public List<BuyingInformation> inputBuyingInformation() {
        System.out.println(NEW_LINE + BUYING_GUIDE_MESSAGE);
        return validateFormat(Console.readLine());
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
}
