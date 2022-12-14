package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.LoginDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.TokensDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.MicroserviceException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeoutException;

@FeignClient(name = "authenticationService", fallbackFactory = AuthenticationClient.Fallback.class)
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


    @Component
    class Fallback implements FallbackFactory<AuthenticationClient> {
        @Override
        public AuthenticationClient create(Throwable cause) {
            if (cause instanceof TimeoutException) {
                throw new MicroserviceException("Service is unavailable");
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unhandled exception: " + cause.getMessage());
            }
        }
    }
}
