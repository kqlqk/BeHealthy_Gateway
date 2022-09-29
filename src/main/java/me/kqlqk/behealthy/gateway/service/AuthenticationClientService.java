package me.kqlqk.behealthy.gateway.service;

import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationClientService {
    UserAuthDTO getUserFromContext();

}
