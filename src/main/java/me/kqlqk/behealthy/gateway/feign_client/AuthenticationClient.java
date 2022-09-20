package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "AuthenticationService")
public interface AuthenticationClient {
    @GetMapping("/api/v1/users/{id}")
    UserDTO getUserById(@PathVariable long id);

    @PostMapping("/api/v1/users")
    void createUser(@RequestBody UserDTO userDTO);

    @PostMapping("/api/v1/users/{id}")
    void updateUser(@PathVariable long id, @RequestBody UserDTO userDTO);

    @GetMapping("/api/v1/users")
    List<UserDTO> getAllUsers();
}
