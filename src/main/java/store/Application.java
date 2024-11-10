package store;

import java.util.List;
import store.domain.BuyingInformation;
import store.domain.controller.ConvenienceStoreController;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();
        convenienceStoreController.initialize();
        List<BuyingInformation> buyingInformation = convenienceStoreController.buyProduct();

        convenienceStoreController.calculateProcess(buyingInformation);
        int membershipSale = convenienceStoreController.applyMembership(buyingInformation);

        convenienceStoreController.printReceipt(buyingInformation, membershipSale);
        convenienceStoreController.askAdditionalItems();
    }
}
