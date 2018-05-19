package com.caesar_84mx.springbootRestApi.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    SUPERUSER,
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
