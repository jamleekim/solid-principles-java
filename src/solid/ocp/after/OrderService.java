package solid.ocp.after;

/**
 * OrderService는 DiscountPolicy 인터페이스에만 의존
 * 어떤 할인 정책이 오든 이 클래스는 수정할 필요가 없음
 */
public class OrderService {
    private final DiscountPolicy discountPolicy;

    public OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public double calculateFinalPrice(double price) {
        return discountPolicy.applyDiscount(price);
    }

    // 사용 예시
    public static void main(String[] args) {
        // VIP 고객 주문
        OrderService vipOrder = new OrderService(new VipDiscount());
        System.out.println("VIP 가격: " + vipOrder.calculateFinalPrice(10000));

        // 시즌 할인 주문 — 기존 코드 수정 없이 새 정책 적용!
        OrderService seasonalOrder = new OrderService(new SeasonalDiscount());
        System.out.println("시즌 가격: " + seasonalOrder.calculateFinalPrice(10000));

        // 쿠폰 적용 주문
        OrderService couponOrder = new OrderService(new CouponDiscount(3000));
        System.out.println("쿠폰 가격: " + couponOrder.calculateFinalPrice(10000));
    }
}
