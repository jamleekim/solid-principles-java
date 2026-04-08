package solid.dip.after;

/**
 * DIP 적용: 고수준 모듈이 정의하는 추상화
 * 이 인터페이스는 "고수준(비즈니스)" 쪽에서 정의하고,
 * "저수준(인프라)" 쪽에서 구현한다 → 의존 방향이 역전!
 */
public interface OrderRepository {
    void save(String orderId, String item);
}
