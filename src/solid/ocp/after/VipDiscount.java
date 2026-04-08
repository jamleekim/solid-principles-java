package solid.ocp.after;

public class VipDiscount implements DiscountPolicy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.8; // 20% 할인
    }
}
