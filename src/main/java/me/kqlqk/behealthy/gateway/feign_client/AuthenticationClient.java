package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "authenticationService")
public interface AuthenticationClient {

    @GetMapping("/api/v1/users/{id}")
    UserAuthDTO getUserById(@PathVariable long id);

    @GetMapping("/api/v1/users")
    List<UserAuthDTO> getAllUsers();

    @GetMapping("/api/v1/users")
    UserAuthDTO getUserByEmail(@RequestParam String email);

    @PostMapping("/api/v1/users")
    void createUser(@RequestBody UserAuthDTO userAuthDTO);

    @PutMapping("/api/v1/users/{id}")
    void updateUser(@PathVariable long id, @RequestBody UserAuthDTO userAuthDTO);

    @PostMapping("/api/v1/users/{id}/password/check")
    ValidateDTO checkPassword(@PathVariable long id, String decodedPassword);

    @GetMapping("/api/v1/auth/validate/access")
    ValidateDTO validateAccessTokenFromRequest(@RequestHeader("Authorization_access") String accessToken);

    @GetMapping("/api/v1/auth/validate/refresh")
    ValidateDTO validateRefreshTokenFromRequest(@RequestHeader("Authorization_refresh") String refreshToken);

    @PutMapping("/api/v1/users/{id}/tokens")
    Map<String, String> updateTokensForUser(@PathVariable long id);

    @GetMapping("/api/v1/auth/request/refresh/email")
    Map<String, String> getEmailFromRefreshToken(@RequestHeader("Authorization_refresh") String refreshToken);

    @PutMapping("/api/v1/users/{id}/access")
    Map<String, String> getNewAccessToken(@PathVariable long id);

    @PutMapping("/api/v1/users/{id}/refresh")
    Map<String, String> getNewRefreshToken(@PathVariable long id);
}
