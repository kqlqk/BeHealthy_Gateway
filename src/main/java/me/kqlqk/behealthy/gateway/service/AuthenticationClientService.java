package me.kqlqk.behealthy.gateway.service;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationClientService {
    boolean existsByEmail(String email);

    UserDTO getByEmail(String email);

    UserDTO getUserFromContext();

}
