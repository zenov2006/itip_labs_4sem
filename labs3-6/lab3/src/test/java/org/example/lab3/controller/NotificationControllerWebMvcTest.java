package org.example.lab3.controller;

import org.example.lab3.model.dto.NotificationDto;
import org.example.lab3.model.entity.Notification;
import org.example.lab3.model.entity.User;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import org.example.lab3.repository.UserRepository;
import org.example.lab3.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@Import(TestSecurityConfig.class)
class NotificationControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldGetNotificationById() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(1L);

        Notification notification = new Notification();
        notification.setId(id);
        notification.setTitle("Test Title");
        notification.setMessage("Test Message");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setRecipient(user);

        when(notificationService.getNotificationById(id)).thenReturn(notification);

        mockMvc.perform(get("/notifications/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.message").value("Test Message"))
                .andExpect(jsonPath("$.channel").value("EMAIL"));
    }

    @Test
    void shouldCreateNotification() throws Exception {
        User user = new User();
        user.setId(1L);

        Notification savedNotification = new Notification();
        savedNotification.setId(10L);
        savedNotification.setTitle("New");
        savedNotification.setMessage("Hello");
        savedNotification.setChannel(NotificationChannel.SMS);
        savedNotification.setStatus(NotificationStatus.CREATED);
        savedNotification.setRecipient(user);   // <-- обязательно

        when(notificationService.createNotification(any(NotificationDto.class))).thenReturn(savedNotification);

        String json = "{\"title\":\"New\",\"message\":\"Hello\",\"channel\":\"SMS\",\"recipientId\":1}";

        mockMvc.perform(post("/notifications/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"))
                .andExpect(jsonPath("$.message").value("Hello"));
    }
}