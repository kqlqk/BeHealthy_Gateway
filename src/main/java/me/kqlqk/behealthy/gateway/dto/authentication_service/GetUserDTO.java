package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.Data;

@Data
public class GetUserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
}
