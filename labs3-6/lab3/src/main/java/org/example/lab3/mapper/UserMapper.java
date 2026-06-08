package org.example.lab3.mapper;

import org.example.lab3.model.dto.UserDto;
import org.example.lab3.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .deviceToken(user.getDeviceToken())
                .telegramChatId(user.getTelegramChatId())
                .createdAt(user.getCreatedAt())
                .build();
    }

}