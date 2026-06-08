package org.example.lab3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class NotificationManager {
    private final Map<String, MessageService> messageServiceMap;

    @Autowired
    public NotificationManager(Map<String, MessageService> messageServiceMap) {
        this.messageServiceMap = messageServiceMap;
    }

    public void notify(String message, String recipient, String serviceType) {
        MessageService service = messageServiceMap.get(serviceType);
        if (service != null) {
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Сервис с именем '" + serviceType + "' не найден");
        }
    }
}