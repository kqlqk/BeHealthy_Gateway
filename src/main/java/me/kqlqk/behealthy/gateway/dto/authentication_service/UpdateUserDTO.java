package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String name;
    private String email;
    private String oldPassword;
    private String password;
}
