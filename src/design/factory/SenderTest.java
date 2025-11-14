package design.factory;

import design.factory.sender.NotificationSender;

public class SenderTest {
    public static void main(String[] args) {
        NotificationFactory notificationFactory = new EmailNotificationFactory();
        NotificationSender sender = notificationFactory.createSender();
        sender.send("Hello");
    }
}
