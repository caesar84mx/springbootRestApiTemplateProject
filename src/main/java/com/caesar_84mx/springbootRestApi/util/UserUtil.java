package com.caesar_84mx.springbootRestApi.util;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.dto.ProfileDto;
import com.caesar_84mx.springbootRestApi.dto.UserUpdateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    private UserUtil() {}

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());

        return user;
    }

    public static User updateFromUpdateRequest(UserUpdateRequestDto updateRequest, User user) {
        user.setAvatarUrl(updateRequest.getAvatarUrl());
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setDescription(updateRequest.getDescription());

        return user;
    }

    public static List<ProfileDto> usersToProfiles(List<User> users) {
        return users.stream()
                .map(ProfileDto::new)
                .collect(Collectors.toList());
    }
}
