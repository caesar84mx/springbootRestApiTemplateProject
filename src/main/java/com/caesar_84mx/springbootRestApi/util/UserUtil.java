package com.caesar_84mx.springbootRestApi.util;

import com.caesar_84mx.springbootRestApi.domain.model.User;

public class UserUtil {
    private UserUtil() {}

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());

        return user;
    }
}
