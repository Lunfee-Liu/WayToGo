package design.factory;

import design.factory.sender.NotificationSender;

public abstract class NotificationFactory {
    abstract NotificationSender createSender();
}
