package store.domain;

public class Membership {

    private int membershipLimit = 0;

    private static final Membership membership = new Membership();

    public Membership() {
    }

    public static Membership getInstance() {
        return membership;
    }

    public void initialize() {
        membershipLimit = 8000;
    }

    public int useMembership(int purchaseAmount) {
        double discount = purchaseAmount * 0.3;

        if (discount >= membershipLimit) {
            discount = membershipLimit;
        }

        membershipLimit -= (int) discount;

        return (int) discount;
    }
}
