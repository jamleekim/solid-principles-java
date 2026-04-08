package solid.ocp.after;

/**
 * 새 할인 정책 추가: 기존 코드를 전혀 수정하지 않고 클래스만 추가!
 * 이것이 OCP의 핵심 — 확장에 열려 있고, 수정에 닫혀 있다.
 */
public class SeasonalDiscount implements DiscountPolicy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.85; // 시즌 15% 할인
    }
}
