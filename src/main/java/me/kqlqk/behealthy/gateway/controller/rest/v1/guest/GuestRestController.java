package me.kqlqk.behealthy.gateway.controller.rest.v1.guest;

import me.kqlqk.behealthy.gateway.dto.authenticationService.TokensDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
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
    public TokensDTO logInUser(@RequestBody UserDTO userDTO) {
        return authenticationClient.login(userDTO);
    }

    @PostMapping("/registration")
    public TokensDTO createUser(@RequestBody UserDTO userDTO) {
        return authenticationClient.registration(userDTO);
    }

    @PostMapping("/access")
    public TokensDTO getNewAccessToken(@RequestBody TokensDTO tokensDTO) {
        return authenticationClient.getNewAccessToken(tokensDTO);
    }

    @PostMapping("/update")
    public TokensDTO updateTokens(@RequestBody TokensDTO tokensDTO) {
        return authenticationClient.updateTokens(tokensDTO);
    }
}
