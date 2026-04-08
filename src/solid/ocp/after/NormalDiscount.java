package solid.ocp.after;

public class NormalDiscount implements DiscountPolicy {
    @Override
    public double applyDiscount(double price) {
        return price; // 할인 없음
    }
}
