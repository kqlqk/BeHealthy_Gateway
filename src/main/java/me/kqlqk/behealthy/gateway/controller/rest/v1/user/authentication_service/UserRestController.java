package me.kqlqk.behealthy.gateway.controller.rest.v1.user.authentication_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.authentication_service.CheckPasswordDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.GetUserDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.UpdateUserDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public UserRestController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @CheckUserId
    @GetMapping("/users/{id}")
    public GetUserDTO getCurrentUser(@PathVariable long id) {
        return authenticationClient.getUserById(id);
    }

    @CheckUserId
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id,
                                        @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getPassword() != null) {
            CheckPasswordDTO checkPasswordDTO = new CheckPasswordDTO(updateUserDTO.getOldPassword());

            if (!authenticationClient.checkPassword(id, checkPasswordDTO).isValid()) {
                throw new RuntimeException("Old password isn't correct");
            }
        }

        authenticationClient.updateUser(id, updateUserDTO);

        return ResponseEntity.ok().build();
    }

}
