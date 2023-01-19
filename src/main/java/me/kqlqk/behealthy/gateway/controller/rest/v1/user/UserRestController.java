package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.authenticationService.ChangePasswordDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
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
    @JsonView(UserDTO.WithoutPasswordView.class)
    public UserDTO getCurrentUser(@PathVariable long id) {
        return authenticationClient.getUserById(id);
    }

    @CheckUserId
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updatePasswordForCurrentUser(@PathVariable long id,
                                                          @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        UserDTO user = authenticationClient.getUserById(id);

        UserDTO passwordDTO = new UserDTO();
        passwordDTO.setPassword(changePasswordDTO.getOldPassword());
        if (!authenticationClient.checkPassword(id, passwordDTO).isValid()) {
            throw new RuntimeException("Old password isn't correct");
        }

        user.setPassword(changePasswordDTO.getNewPassword());

        authenticationClient.updateUser(id, user);

        return ResponseEntity.ok().build();
    }

}
