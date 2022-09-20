package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class GuestRestController {
    private final AuthenticationClient authenticationClient;
    private final AuthenticationService authenticationService;

    @Autowired
    public GuestRestController(AuthenticationClient authenticationClient, AuthenticationService authenticationService) {
        this.authenticationClient = authenticationClient;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        if (authenticationService.existsByEmail(userDTO.getEmail())) {
            throw new UserException("User with email = " + userDTO.getEmail() + " already exists");
        }

        authenticationClient.createUser(userDTO);
        return ResponseEntity.ok().build();
    }


}
