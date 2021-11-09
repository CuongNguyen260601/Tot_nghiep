package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.response.UserResponseDTO;
import com.localbrand.entity.User;
import com.localbrand.model_mapping.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapping implements Mapping<UserResponseDTO, User> {
    @Override
    public UserResponseDTO toDto(User user) {
        return null;
    }

    @Override
    public User toEntity(UserResponseDTO userResponseDTO) {
        return null;
    }
}
