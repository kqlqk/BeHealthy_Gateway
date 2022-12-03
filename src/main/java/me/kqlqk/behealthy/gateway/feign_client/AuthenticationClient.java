package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.LoginDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.TokensDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "authenticationService")
public interface AuthenticationClient {

    @GetMapping("/api/v1/users/{id}")
    UserDTO getUserById(@PathVariable long id);

    @GetMapping("/api/v1/users")
    UserDTO getUserByEmail(@RequestParam String email);

    @PutMapping("/api/v1/users/{id}")
    void updateUser(@PathVariable long id, @RequestBody UserDTO userDTO);

    @PostMapping("/api/v1/users/{id}/password/check")
    ValidateDTO checkPassword(@PathVariable long id, String decodedPassword);

    @PostMapping("/api/v1/auth/access")
    Map<String, String> getNewAccessToken(@RequestBody TokensDTO tokensDTO);

    @PostMapping("/api/v1/auth/access/validate")
    ValidateDTO validateAccessToken(@RequestBody TokensDTO tokensDTO);

    @PostMapping("/api/v1/auth/access/email")
    Map<String, String> getEmailFromAccessToken(@RequestBody TokensDTO tokensDTO);

    @PostMapping("/api/v1/auth/update")
    TokensDTO updateTokens(@RequestBody TokensDTO tokensDTO);

    @PostMapping("/api/v1/auth/login")
    Map<String, String> login(@RequestBody LoginDTO loginDTO);

    @PostMapping("/api/v1/auth/registration")
    Map<String, String> registration(@RequestBody UserDTO userDTO);
}
