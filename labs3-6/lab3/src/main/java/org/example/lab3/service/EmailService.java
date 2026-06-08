package org.example.lab3.service;

import org.springframework.stereotype.Service;

@Service("customEmail")   // вместо имени по умолчанию "emailService"
public class EmailService implements MessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("EMAIL to " + recipient + ": " + message);
    }
}