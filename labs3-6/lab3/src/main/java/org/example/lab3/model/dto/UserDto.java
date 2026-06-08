package org.example.lab3.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 100, message = "Имя не должно быть длиннее 100 символов")
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email не соответствует шаблону")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Неверный формат телефона")
    private String phone;

    private String deviceToken;
    private String telegramChatId;
    private LocalDateTime createdAt;
}