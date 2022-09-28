package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final AuthenticationClient authenticationClient;
    private final AuthenticationClientService authenticationClientService;

    @Autowired
    public UserRestController(AuthenticationClient authenticationClient, AuthenticationClientService authenticationClientService) {
        this.authenticationClient = authenticationClient;
        this.authenticationClientService = authenticationClientService;
    }

    @GetMapping("/users/{id}")
    public UserDTO getCurrentUser(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return authenticationClient.getUserById(id);
    }
}
