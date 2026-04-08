package solid.dip.after;

/** 저수준 모듈: MongoDB 구현체 — OrderService 수정 없이 교체 가능! */
public class MongoOrderRepository implements OrderRepository {
    @Override
    public void save(String orderId, String item) {
        System.out.println("[MongoDB] 주문 저장: " + orderId + " - " + item);
    }
}
