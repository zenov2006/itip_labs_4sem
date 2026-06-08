package org.example.lab3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab3.model.dto.NotificationDto;
import org.example.lab3.model.entity.Notification;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import org.example.lab3.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .recipientId(notification.getRecipient().getId())
                .build();
    }

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        Notification notification = notificationService.createNotification(request);
        return mapToDto(notification);
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        return mapToDto(notificationService.getNotificationById(id));
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id,
                                              @RequestBody @Valid NotificationDto request) {
        Notification notification = notificationService.updateNotification(id, request);
        return mapToDto(notification);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getByChannel(channel).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipient(@PathVariable Long recipientId) {
        return notificationService.getByRecipientId(recipientId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Дополнительные endpoints
    @GetMapping("/filter")
    public List<NotificationDto> filterByStatusAndChannel(
            @RequestParam NotificationStatus status,
            @RequestParam NotificationChannel channel) {
        return notificationService.getByStatusAndChannel(status, channel).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/sorted/{status}")
    public List<NotificationDto> getSortedByCreatedAt(@PathVariable NotificationStatus status) {
        return notificationService.getByStatusOrderByCreatedAtAsc(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/recipient/{recipientId}/status/{status}")
    public List<NotificationDto> getByRecipientAndStatus(@PathVariable Long recipientId,
                                                         @PathVariable NotificationStatus status) {
        return notificationService.getByRecipientIdAndStatus(recipientId, status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}   