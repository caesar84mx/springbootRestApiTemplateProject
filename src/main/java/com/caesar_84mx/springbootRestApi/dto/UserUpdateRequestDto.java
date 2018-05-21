package com.caesar_84mx.springbootRestApi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Size;

@Data
public class UserUpdateRequestDto {
    @URL
    private String avatarUrl;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    private String description;
}
