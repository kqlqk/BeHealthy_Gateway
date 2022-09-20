package me.kqlqk.behealthy.gateway.service.impl;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        List<UserDTO> users = authenticationClient.getAllUsers();

        for (UserDTO user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }
}
