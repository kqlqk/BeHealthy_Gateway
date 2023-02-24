package me.kqlqk.behealthy.gateway.feign_client;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.*;
import me.kqlqk.behealthy.gateway.exception.RuntimeNotWrappedByHystrixException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "authenticationService", fallbackFactory = AuthenticationClient.Fallback.class)
public interface AuthenticationClient {

    @GetMapping("/api/v1/users/{id}")
    GetUserDTO getUserById(@PathVariable long id);

    @GetMapping("/api/v1/users")
    GetUserDTO getUserByEmail(@RequestParam String email);

    @PutMapping("/api/v1/users/{id}")
    void updateUser(@PathVariable long id, @RequestBody UpdateUserDTO updateUserDTO);

    @PostMapping("/api/v1/users/{id}/password/check")
    ValidateDTO checkPassword(@PathVariable long id, @RequestBody CheckPasswordDTO checkPasswordDTO);

    @PostMapping("/api/v1/auth/access")
    TokensDTO getNewAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO);

    @PostMapping("/api/v1/auth/access/validate")
    ValidateDTO validateAccessToken(@RequestBody AccessTokenDTO accessTokenDTO);

    @PostMapping("/api/v1/auth/access/email")
    Map<String, String> getEmailFromAccessToken(@RequestBody AccessTokenDTO accessTokenDTO);

    @PostMapping("/api/v1/auth/update")
    TokensDTO updateTokens(@RequestBody RefreshTokenDTO refreshTokenDTO);

    @PostMapping("/api/v1/auth/login")
    TokensDTO login(@RequestBody LoginDTO loginDTO);

    @PostMapping("/api/v1/auth/registration")
    TokensDTO registration(@RequestBody RegistrationDTO registrationDTO);


    @Component
    @Slf4j
    class Fallback implements FallbackFactory<AuthenticationClient> {
        @Override
        public AuthenticationClient create(Throwable cause) {
            log.warn("Something went wrong: ", cause);

            throw new RuntimeNotWrappedByHystrixException("Service is unavailable");
        }
    }
}
