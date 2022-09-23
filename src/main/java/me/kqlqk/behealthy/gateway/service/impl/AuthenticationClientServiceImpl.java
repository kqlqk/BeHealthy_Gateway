package me.kqlqk.behealthy.gateway.service.impl;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationClientServiceImpl implements AuthenticationClientService {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public AuthenticationClientServiceImpl(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        return authenticationClient.getAllUsers()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public UserDTO getByEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        return authenticationClient.getAllUsers()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElseThrow(() -> new UserException("User with email = " + email + " not found"));
    }

    @Override
    public UserDTO getUserFromContext() {
        String email;

        try {
            email = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException e) {
            throw new UserException("Authentication not found for user");
        }

        return getByEmail(email);
    }
}
