package solid.ocp.after;

public class CouponDiscount implements DiscountPolicy {
    private final double couponAmount;

    public CouponDiscount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    @Override
    public double applyDiscount(double price) {
        return Math.max(0, price - couponAmount);
    }
}
