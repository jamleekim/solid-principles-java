package solid.dip.after;

/**
 * DIP 적용: OrderService는 추상화(인터페이스)에만 의존
 *
 * 의존 방향 비교:
 *   Before: OrderService → MySqlOrderRepository (고수준 → 저수준)
 *   After:  OrderService → OrderRepository ← MySqlOrderRepository
 *                          (고수준 → 추상화 ← 저수준)
 *           → 저수준이 추상화에 의존하도록 방향이 역전됨!
 *
 * 이점:
 * - DB 교체: 생성자에 다른 구현체를 넘기면 끝 (코드 수정 없음)
 * - 테스트: Mock 구현체를 넘기면 DB 없이 테스트 가능
 * - 유연성: 런타임에 구현체를 교체할 수도 있음
 */
public class OrderService {
    private final OrderRepository repository;
    private final NotificationSender notificationSender;

    // 생성자 주입: 외부에서 구현체를 결정
    public OrderService(OrderRepository repository, NotificationSender notificationSender) {
        this.repository = repository;
        this.notificationSender = notificationSender;
    }

    public void placeOrder(String orderId, String item) {
        repository.save(orderId, item);
        notificationSender.send("주문 완료: " + item);
        System.out.println("주문 처리 완료: " + orderId);
    }

    // 사용 예시
    public static void main(String[] args) {
        // MySQL + Email 조합
        System.out.println("=== MySQL + Email ===");
        OrderService service1 = new OrderService(
            new MySqlOrderRepository(),
            new EmailNotificationSender()
        );
        service1.placeOrder("ORD-001", "MacBook Pro");

        // MongoDB + 카카오톡 조합 — OrderService 코드 변경 없이 교체!
        System.out.println("\n=== MongoDB + 카카오톡 ===");
        OrderService service2 = new OrderService(
            new MongoOrderRepository(),
            new KakaoNotificationSender()
        );
        service2.placeOrder("ORD-002", "iPhone 16");
    }
}
