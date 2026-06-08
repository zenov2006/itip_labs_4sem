package org.example.lab3.service;

import org.example.lab3.model.dto.NotificationDto;
import org.example.lab3.model.entity.Notification;
import org.example.lab3.model.entity.User;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import org.example.lab3.repository.NotificationRepository;
import org.example.lab3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    // Часть 6: тест создания уведомления (позитивный)
    @Test
    void shouldCreateNotification() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("ivan@example.com");

        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Завтра лекция")
                .channel(NotificationChannel.EMAIL)
                .recipientId(userId)
                .build();

        Notification savedNotification = new Notification();
        savedNotification.setTitle(dto.getTitle());
        savedNotification.setMessage(dto.getMessage());
        savedNotification.setChannel(dto.getChannel());
        savedNotification.setStatus(NotificationStatus.CREATED);
        savedNotification.setRecipient(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);

        Notification result = notificationService.createNotification(dto);

        assertNotNull(result);
        assertEquals("Напоминание", result.getTitle());
        assertEquals(NotificationChannel.EMAIL, result.getChannel());
        assertEquals(user, result.getRecipient());
    }

    // Часть 7 / Задание 4: негативный тест – получатель не найден
    @Test
    void shouldThrowWhenUserNotFound() {
        Long missingUserId = 99L;
        NotificationDto dto = NotificationDto.builder()
                .title("Тест")
                .message("Сообщение")
                .channel(NotificationChannel.SMS)
                .recipientId(missingUserId)
                .build();

        when(userRepository.findById(missingUserId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.createNotification(dto));
    }

    // Задание 3: тест для getNotificationById()
    @Test
    void shouldGetNotificationById() {
        Long notificationId = 10L;
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setTitle("Тестовое уведомление");

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Notification found = notificationService.getNotificationById(notificationId);

        assertNotNull(found);
        assertEquals(notificationId, found.getId());
        assertEquals("Тестовое уведомление", found.getTitle());
    }

    // Задание 4: негативный тест – уведомление не найдено
    @Test
    void shouldThrowWhenNotificationNotFound() {
        Long wrongId = 999L;
        when(notificationRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.getNotificationById(wrongId));
    }
}