package me.kqlqk.behealthy.gateway.dto.authenticationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokensDTO {
    private long userId;
    private String accessToken;
    private String refreshToken;

    public TokensDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
