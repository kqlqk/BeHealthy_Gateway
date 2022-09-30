package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.authenticationService.ChangePasswordDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.feign_client.KcalCounterClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final AuthenticationClient authenticationClient;
    private final KcalCounterClient kcalCounterClient;
    private final AuthenticationClientService authenticationClientService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(AuthenticationClient authenticationClient,
                              KcalCounterClient kcalCounterClient,
                              AuthenticationClientService authenticationClientService, PasswordEncoder passwordEncoder) {
        this.authenticationClient = authenticationClient;
        this.kcalCounterClient = kcalCounterClient;
        this.authenticationClientService = authenticationClientService;
        this.passwordEncoder = passwordEncoder;
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
    public void updatePasswordForCurrentUser(@PathVariable long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        UserAuthDTO user = authenticationClient.getUserById(id);

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), authenticationClient.getUserById(id).getPassword())) {
            throw new UserException("Old password isn't correct");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));

        authenticationClient.updateUser(id, user);
    }

    @GetMapping("/users/{id}/condition")
    @JsonView(UserConditionDTO.WithoutUserIdView.class)
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getUserConditionByUserId(id);
    }

    @GetMapping("/users/{id}/kcals")
    public KcalsInfoDTO getCurrentUserKcalsInfo(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getKcalsInfoByUserId(id);
    }

    @PostMapping("/users/{id}/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id, @RequestBody @Valid UserConditionDTO userConditionDTO) {
        userConditionDTO.setUserId(id);
        kcalCounterClient.createUserCondition(userConditionDTO);

        return ResponseEntity.ok().build();
    }

}
