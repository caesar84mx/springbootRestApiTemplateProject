package com.caesar_84mx.springbootRestApi.dto.user_related;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 5)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 25)
    private String nickname;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String lastName;

    private String description;

    private String avatarUrl;
}

