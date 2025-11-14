package design.factory.sender;

public class EmailNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        System.out.println("send by email: " + message);
    }
}
