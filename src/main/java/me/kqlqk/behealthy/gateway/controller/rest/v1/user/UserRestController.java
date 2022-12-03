package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.authenticationService.ChangePasswordDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
                                                          @RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                                          HttpServletResponse response) {
        UserDTO user = authenticationClient.getUserById(id);

        if (!authenticationClient.checkPassword(id, changePasswordDTO.getOldPassword()).isValid()) {
            throw new UserException("Old password isn't correct");
        }

        user.setPassword(changePasswordDTO.getNewPassword());

        authenticationClient.updateUser(id, user);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

}
