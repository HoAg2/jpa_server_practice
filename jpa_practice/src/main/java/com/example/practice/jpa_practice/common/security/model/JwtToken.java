package com.example.practice.jpa_practice.common.security.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JwtToken {
    String jwtTokenType;
    String id;
    String loginSessionId;
    String accessToken;
    LocalDateTime accessTokenExpiration;
    Long accessTokenExpiresIn;
    String refreshToken;
    LocalDateTime refreshTokenExpiration;
    Long refreshTokenExpiresIn;
    Integer role;
    String channel;
    String service;
}
