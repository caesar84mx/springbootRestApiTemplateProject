package com.caesar_84mx.springbootRestApi.dto.user_related;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NonNull
    @Setter
    @Getter
    private String authName;
    @NonNull
    @Setter
    @Getter
    private String password;
}
