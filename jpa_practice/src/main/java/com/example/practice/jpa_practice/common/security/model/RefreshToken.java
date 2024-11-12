package com.example.practice.jpa_practice.common.security.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RefreshToken {
    String refreshToken;
    Date refreshTokenExpiration;
}
