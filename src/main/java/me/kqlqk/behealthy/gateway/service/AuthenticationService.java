package me.kqlqk.behealthy.gateway.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    boolean existsByEmail(String email);
}
