package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class RefreshTokenDTO {
    private final String type = "Bearer";

    private String refreshToken;
}
