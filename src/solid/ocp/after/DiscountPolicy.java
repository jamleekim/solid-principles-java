package solid.ocp.after;

/**
 * OCP 적용: 할인 정책을 인터페이스로 추상화
 * 새로운 할인이 필요하면 이 인터페이스를 구현하는 클래스를 추가하면 됨
 * → 기존 코드 수정 없이 확장 가능
 */
public interface DiscountPolicy {
    double applyDiscount(double price);
}
