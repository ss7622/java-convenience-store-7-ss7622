package store.domain.controller;

import java.io.IOException;
import java.util.List;
import store.domain.BuyingInformation;
import store.domain.Membership;
import store.domain.Products;
import store.domain.TotalPriceAndCount;
import store.domain.TotalPromotionInformation;
import store.service.Initializer;
import store.service.PriceCalculator;
import store.service.PromotionManager;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final Initializer initializer = new Initializer();
    private static PromotionManager promotionManager = new PromotionManager();
    private static TotalPriceAndCount totalPriceAndCount = new TotalPriceAndCount();
    private static TotalPromotionInformation totalPromotionInformation = new TotalPromotionInformation();


    public void initialize() {
        try {
            initializer.promotions();
            initializer.products();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BuyingInformation> buyProduct() {
        outputView.printWelcomeMessage();
        outputView.printProductsGuideMessage();
        while (true) {
            try {
                return inputView.inputBuyingInformation();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void calculateProcess(List<BuyingInformation> buyingInformation) {
        initializeProcess();
        for (BuyingInformation information : buyingInformation) {
            promotionManager.divideNormalAndPromotionProduct(information);
            cantApplyBenefit(information);
            checkCanGetBenefit(information);
            reduceQuantity(information);
            calculatePurchaseAmount(information);
        }
    }

    public int applyMembership(List<BuyingInformation> buyingInformation) {
        Membership membership = Membership.getInstance();

        String answer = inputView.inputMembership();
        int membershipSale = 0;
        if (answer.equals("Y")) {
            membershipSale = membership.useMembership(PriceCalculator.totalNormalPrice(buyingInformation));
        }
        return membershipSale;
    }

    public void printReceipt(List<BuyingInformation> buyingInformation, int membershipSale) {
        outputView.printBuyingInformationMessage(buyingInformation);
        outputView.printPromotionInformation(totalPromotionInformation);
        outputView.printTotalInformation(totalPriceAndCount, totalPromotionInformation, membershipSale);
    }

    public void askAdditionalItems() {
        String answer = inputView.inputRetry();
        if (answer.equals("Y")) {
            List<BuyingInformation> buyingInformation = buyProduct();
            calculateProcess(buyingInformation);
            int membershipSale = applyMembership(buyingInformation);
            printReceipt(buyingInformation, membershipSale);
            askAdditionalItems();
        }
    }

    private void calculatePurchaseAmount(BuyingInformation information) {
        totalPriceAndCount.addTotalPrice(PriceCalculator.totalPrice(information));
        totalPriceAndCount.addCount(information.getTotalCount());

        promotionManager.addPromotionProduct(totalPromotionInformation, information);
    }

    private void reduceQuantity(BuyingInformation information) {
        Products.reduceQuantity(information.getNormalCount(), information.getName(), "null");
        Products.reduceQuantity(information.getPromotionCount(), information.getName(),
                Products.getPromotion(information.getName()));
    }

    private void cantApplyBenefit(BuyingInformation information) {
        int cantApplyPromotionCount = promotionManager.cantApplyPromotionCount(information);
        if (cantApplyPromotionCount != 0) {
            String answer = inputView.inputNonPromotion(information.getName(),
                    cantApplyPromotionCount + information.getNormalCount());
            if (answer.equals("N")) {
                information.adjustCount(0, information.getPromotionCount() - cantApplyPromotionCount);
                return;
            }
            information.adjustCount(cantApplyPromotionCount + information.getNormalCount(),
                    information.getPromotionCount() - cantApplyPromotionCount);
        }
    }

    private void checkCanGetBenefit(BuyingInformation information) {
        int canGetFreeCount = promotionManager.canGetFreeCount(information);
        if (canGetFreeCount != 0) {
            String answer = inputView.inputGetAdditionalProduct(information.getName(), canGetFreeCount);

            if (answer.equals("Y")) {
                information.adjustCount(information.getNormalCount(), information.getPromotionCount() + 1);
            }
        }
    }

    private void initializeProcess() {
        promotionManager = new PromotionManager();
        totalPriceAndCount = new TotalPriceAndCount();
        totalPromotionInformation = new TotalPromotionInformation();
    }

}
