package solid.dip.after;

public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("[Email] 발송: " + message);
    }
}
