package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.authenticationService.ChangePasswordDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final AuthenticationClient authenticationClient;
    private final AuthenticationClientService authenticationClientService;

    @Autowired
    public UserRestController(AuthenticationClient authenticationClient,
                              AuthenticationClientService authenticationClientService,
                              PasswordEncoder passwordEncoder) {
        this.authenticationClient = authenticationClient;
        this.authenticationClientService = authenticationClientService;
    }

    @GetMapping("/users/{id}")
    @JsonView(UserAuthDTO.WithoutPasswordView.class)
    public UserAuthDTO getCurrentUser(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return authenticationClient.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updatePasswordForCurrentUser(@PathVariable long id,
                                                          @RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                                          HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        UserAuthDTO user = authenticationClient.getUserById(id);

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
