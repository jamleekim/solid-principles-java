package solid.ocp.before;

/**
 * OCP 위반 예시: 새로운 할인 타입이 추가될 때마다 이 클래스를 수정해야 함
 *
 * 문제점:
 * - "SEASONAL" 할인을 추가하려면? → 이 메서드에 else-if를 추가해야 함
 * - 기존 코드 수정 = 기존 기능에 버그가 생길 위험
 * - 할인 타입이 늘어날수록 메서드가 비대해짐
 */
public class DiscountCalculator {

    public double calculate(String discountType, double price) {
        if ("NORMAL".equals(discountType)) {
            return price;
        } else if ("VIP".equals(discountType)) {
            return price * 0.8;  // 20% 할인
        } else if ("SUPER_VIP".equals(discountType)) {
            return price * 0.7;  // 30% 할인
        } else if ("COUPON".equals(discountType)) {
            return price - 5000; // 5000원 할인
        }
        // 새 할인 타입 추가 시 여기에 else-if를 계속 추가해야 함...
        throw new IllegalArgumentException("Unknown discount type: " + discountType);
    }
}
