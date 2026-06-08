package org.example.lab3.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;

    @NotBlank(message = "Текст сообщения не должен быть пустым")
    private String message;

    @NotNull(message = "Канал обязателен")
    private NotificationChannel channel;

    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    @NotNull(message = "Идентификатор получателя обязателен")
    private Long recipientId;
}