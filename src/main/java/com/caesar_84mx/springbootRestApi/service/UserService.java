package com.caesar_84mx.springbootRestApi.service;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotFoundException;

public interface UserService extends GenericService<User> {
    void setActive(long id, boolean active) throws NotFoundException;
    boolean checkEmailAvailable(String email);
    boolean checkNicknameAvailable(String nickname);
}
