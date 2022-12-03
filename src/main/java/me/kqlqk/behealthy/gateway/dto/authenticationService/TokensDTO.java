package me.kqlqk.behealthy.gateway.dto.authenticationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
}
