package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
