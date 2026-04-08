package solid.dip.after;

/** 저수준 모듈: MySQL 구현체 — 추상화(OrderRepository)에 의존 */
public class MySqlOrderRepository implements OrderRepository {
    @Override
    public void save(String orderId, String item) {
        System.out.println("[MySQL] 주문 저장: " + orderId + " - " + item);
    }
}
