package solid.dip.before;

public class MySqlOrderRepository {
    public void save(String orderId, String item) {
        System.out.println("[MySQL] 주문 저장: " + orderId + " - " + item);
    }
}
