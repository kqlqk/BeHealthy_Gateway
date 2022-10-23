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

    @PostMapping("/api/v1/users/{id}")
    void updateUser(@PathVariable long id, @RequestBody UserAuthDTO userAuthDTO);

    @GetMapping("/api/v1/auth/validate_access_token")
    ValidateDTO validateAccessTokenFromRequest(@RequestHeader("Authorization_access") String accessToken);

    @GetMapping("/api/v1//auth/validate_refresh_token")
    ValidateDTO validateRefreshTokenFromRequest(@RequestHeader("Authorization_refresh") String refreshToken);

    @GetMapping("/api/v1/users/{id}/update_tokens")
    Map<String, String> updateTokensForUser(@PathVariable long id);

    @GetMapping("/api/v1/auth/get_email_from_access_token")
    Map<String, String> getEmailFromAccessToken(@RequestHeader("Authorization_access") String accessToken);

    @GetMapping("/api/v1/auth/get_email_from_refresh_token")
    Map<String, String> getEmailFromRefreshToken(@RequestHeader("Authorization_refresh") String refreshToken);

    @GetMapping("/api/v1/users/{id}/new_access_token")
    Map<String, String> getNewAccessToken(@PathVariable long id);

    @GetMapping("/api/v1/users/{id}/new_refresh_token")
    Map<String, String> getNewRefreshToken(@PathVariable long id);
}
