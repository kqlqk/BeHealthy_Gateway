package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class TokensDTO {
    private final String type = "Bearer";

    private long userId;
    private String accessToken;
    private String refreshToken;
}
