package me.kqlqk.behealthy.gateway.service;

import me.kqlqk.behealthy.gateway.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationClientService {
    UserDTO getUserFromContext();

}
