package solid.dip.before;

/**
 * DIP 위반 예시: 고수준 모듈(OrderService)이 저수준 모듈(MySqlOrderRepository, EmailSender)에 직접 의존
 *
 * 문제점:
 * - MySQL을 MongoDB로 교체하려면? → OrderService 코드를 수정해야 함
 * - 이메일 대신 카카오톡 알림으로 바꾸려면? → OrderService 코드를 수정해야 함
 * - 단위 테스트 시 실제 DB 연결과 이메일 발송이 필요 → 테스트 불가
 */
public class OrderService {
    // 구체 클래스에 직접 의존 (new로 직접 생성!)
    private MySqlOrderRepository repository = new MySqlOrderRepository();
    private EmailSender emailSender = new EmailSender();

    public void placeOrder(String orderId, String item) {
        // 주문 저장 — MySQL에 강하게 결합
        repository.save(orderId, item);

        // 알림 발송 — Email에 강하게 결합
        emailSender.send("주문 완료: " + item);

        System.out.println("주문 처리 완료: " + orderId);
    }
}
