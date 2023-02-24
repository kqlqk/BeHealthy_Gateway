package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String name;
    private String email;
    private String password;
}
