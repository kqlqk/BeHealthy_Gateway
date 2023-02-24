package me.kqlqk.behealthy.gateway.dto.authentication_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPasswordDTO {
    private String password;
}
