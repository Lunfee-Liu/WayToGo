package design.factory;

import design.factory.sender.NotificationSender;
import design.factory.sender.PushNotificationSender;

public class PushNotificationFactory extends NotificationFactory{
    @Override
    NotificationSender createSender() {
        return new PushNotificationSender();
    }
}
