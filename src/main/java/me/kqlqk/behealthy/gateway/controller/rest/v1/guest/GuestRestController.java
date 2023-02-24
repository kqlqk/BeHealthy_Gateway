package me.kqlqk.behealthy.gateway.controller.rest.v1.guest;

import me.kqlqk.behealthy.gateway.dto.authentication_service.LoginDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.RefreshTokenDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.RegistrationDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.TokensDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GuestRestController {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public GuestRestController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @PostMapping("/login")
    public TokensDTO logInUser(@RequestBody LoginDTO loginDTO) {
        return authenticationClient.login(loginDTO);
    }

    @PostMapping("/registration")
    public TokensDTO createUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationClient.registration(registrationDTO);
    }

    @PostMapping("/access")
    public TokensDTO getNewAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return authenticationClient.getNewAccessToken(refreshTokenDTO);
    }

    @PostMapping("/update")
    public TokensDTO updateTokens(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return authenticationClient.updateTokens(refreshTokenDTO);
    }
}
