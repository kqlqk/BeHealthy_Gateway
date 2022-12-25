package me.kqlqk.behealthy.gateway.cfg.filter.public_access;

import lombok.NonNull;
import me.kqlqk.behealthy.gateway.cfg.filter.JWTFilter;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilterPublicAccess extends JWTFilter {

    public JWTFilterPublicAccess(AuthenticationClient authenticationClient,
                                 UserDetailsService userDetailsService) {
        super(authenticationClient, userDetailsService);
    }

    public void doFilterInternalPublic(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       @NonNull FilterChain fc) throws ServletException, IOException {
        doFilterInternal(request, response, fc);
    }
}
