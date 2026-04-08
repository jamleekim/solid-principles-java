package solid.dip.after;

public class KakaoNotificationSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("[카카오톡] 발송: " + message);
    }
}
