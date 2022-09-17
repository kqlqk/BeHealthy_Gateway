package me.kqlqk.behealthy.gateway.controllers.rest.v1;

import me.kqlqk.behealthy.gateway.beans.UserBean;
import me.kqlqk.behealthy.gateway.feign_clients.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GuestRestController {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public GuestRestController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }


    @GetMapping("/me")
    public UserBean getUser() {
        return authenticationClient.geUserById(1);
    }

}
