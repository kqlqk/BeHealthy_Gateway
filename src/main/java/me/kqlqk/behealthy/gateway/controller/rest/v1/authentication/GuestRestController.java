package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication;

import me.kqlqk.behealthy.gateway.dto.LoginDTO;
import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class GuestRestController {
    private final AuthenticationClient authenticationClient;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public GuestRestController(AuthenticationClient authenticationClient, UserService userService, PasswordEncoder encoder) {
        this.authenticationClient = authenticationClient;
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        authenticationClient.createUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInUser(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response) {
        UserDTO savedUser = userService.getByEmail(loginDTO.getEmail());

        if (!encoder.matches(loginDTO.getPassword(), savedUser.getPassword())) {
            throw new UserException("Bad credentials");
        }

        Map<String, String> tokens = authenticationClient.updateTokensForUser(savedUser.getId());

        response.setHeader("Authorization_access", "Bearer_" + tokens.get("access"));
        response.setHeader("Authorization_refresh", "Bearer_" + tokens.get("refresh"));
        return ResponseEntity.ok().build();
    }

}
