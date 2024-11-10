package store.domain;

public class Membership {

    private int membershipLimit = 8000;

    private static final Membership membership = new Membership();

    public Membership() {
    }

    public static Membership getInstance() {
        return membership;
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
