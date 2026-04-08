package solid.dip.before;

public class EmailSender {
    public void send(String message) {
        System.out.println("[Email] 발송: " + message);
    }
}
