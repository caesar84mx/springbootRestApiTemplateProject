package com.caesar_84mx.springbootRestApi.dto.user_related;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType;
}
