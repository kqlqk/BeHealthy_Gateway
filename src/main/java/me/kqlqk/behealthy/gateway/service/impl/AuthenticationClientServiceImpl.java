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
    public UserDTO getUserFromContext() {
        String email;

        try {
            email = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException e) {
            throw new UserException("Authentication not found for user");
        }

        return authenticationClient.getUserByEmail(email);
    }
}
