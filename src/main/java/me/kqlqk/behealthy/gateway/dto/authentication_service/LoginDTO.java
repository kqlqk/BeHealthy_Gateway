package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
