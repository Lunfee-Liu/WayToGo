package design.factory;

import design.factory.sender.EmailNotificationSender;
import design.factory.sender.NotificationSender;

public class EmailNotificationFactory extends NotificationFactory{
    @Override
    NotificationSender createSender() {
        return new EmailNotificationSender();
    }
}
