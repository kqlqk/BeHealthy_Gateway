package me.kqlqk.behealthy.gateway.cfg.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.ExceptionDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.AccessTokenDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final AuthenticationClient authenticationClient;
    private final UserDetailsService userDetailsService;

    private final String[] urisNotToCheck = {
            "/api/v1/registration", "/api/v1/registration/",
            "/api/v1/login", "/api/v1/login/",
            "/api/v1/access", "/api/v1/access/",
            "/api/v1/update", "/api/v1/update/"
    };

    @Autowired
    public JWTFilter(AuthenticationClient authenticationClient, UserDetailsService userDetailsService) {
        this.authenticationClient = authenticationClient;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain fc) throws ServletException, IOException {
        for (String uri : urisNotToCheck) {
            if (request.getRequestURI().equals(uri)) {
                fc.doFilter(request, response);
                return;
            }
        }

        String accessToken;
        try {
            accessToken = getTokenFromRequest(request);
        }
        catch (RuntimeException e) {
            postException(e, response);
            return;
        }

        boolean tokenValid;
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken);
        try {
            tokenValid = authenticationClient.validateAccessToken(accessTokenDTO).isValid();
        }
        catch (RuntimeException e) {
            postException(e, response);
            return;
        }

        if (!tokenValid) {
            postException(new RuntimeException("Token invalid"), response);
            return;
        }

        String email;
        try {
            email = authenticationClient.getEmailFromAccessToken(accessTokenDTO).get("email");
        }
        catch (RuntimeException e) {
            postException(e, response);
            return;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetailsService.loadUserByUsername(email),
                null,
                userDetailsService.loadUserByUsername(email).getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info(email + " was authorized to " + request.getRequestURI() + " ( filter)");

        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
            throw new RuntimeException("Bearer token not found");
        }
        throw new RuntimeException("Authorization header not found");
    }

    private void postException(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        ExceptionDTO exceptionDTO = new ExceptionDTO();

        if (e instanceof HttpMessageNotWritableException) {
            exceptionDTO.setInfo("Required request body is missing");
        }
        else {
            exceptionDTO.setInfo(e.getMessage());
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDTO));
        response.getWriter().flush();
    }
}
