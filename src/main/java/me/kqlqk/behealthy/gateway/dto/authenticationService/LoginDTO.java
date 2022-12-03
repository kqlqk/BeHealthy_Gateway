package me.kqlqk.behealthy.gateway.dto.authenticationService;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
