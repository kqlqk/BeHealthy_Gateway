package me.kqlqk.behealthy.gateway.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final AuthenticationClient authenticationClient;

    @Autowired
    public UserDetailServiceImpl(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        for (UserAuthDTO userAuthDTO : authenticationClient.getAllUsers()) {
            if (userAuthDTO.getEmail().equals(email)) {
                Set<GrantedAuthority> authorities = new HashSet<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                return new org.springframework.security.core.userdetails.User(email, userAuthDTO.getPassword(), authorities);
            }
        }

        throw new UserException("User with email = " + email + " not found");
    }
}
