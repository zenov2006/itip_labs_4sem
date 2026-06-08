package org.example.lab3.service;

import lombok.RequiredArgsConstructor;
import org.example.lab3.model.dto.NotificationDto;
import org.example.lab3.model.entity.Notification;
import org.example.lab3.model.entity.User;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import org.example.lab3.repository.NotificationRepository;
import org.example.lab3.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Notification createNotification(NotificationDto request) {
        User user = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Получатель не найден, id=" + request.getRecipientId()));

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);
        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено, id=" + id));
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto request) {
        Notification notification = getNotificationById(id);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());

        if (request.getStatus() == NotificationStatus.SENT && notification.getStatus() != NotificationStatus.SENT) {
            notification.setSentAt(LocalDateTime.now());
        }
        notification.setStatus(request.getStatus());
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByChannel(NotificationChannel channel) {
        return notificationRepository.findByChannel(channel);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Для самостоятельных заданий
    @Transactional(readOnly = true)
    public List<Notification> getByStatusAndChannel(NotificationStatus status, NotificationChannel channel) {
        return notificationRepository.findByStatusAndChannel(status, channel);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByStatusOrderByCreatedAtAsc(NotificationStatus status) {
        return notificationRepository.findByStatusOrderByCreatedAtAsc(status);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByRecipientIdAndStatus(Long recipientId, NotificationStatus status) {
        return notificationRepository.findByRecipientIdAndStatus(recipientId, status);
    }
}