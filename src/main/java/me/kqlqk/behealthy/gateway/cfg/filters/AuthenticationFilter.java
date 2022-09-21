package me.kqlqk.behealthy.gateway.cfg.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.gateway.dto.ExceptionDTO;
import me.kqlqk.behealthy.gateway.dto.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.TokenException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationClient authenticationClient;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final String[] urisNotToCheck = {
            "/registration",
            "/login"
    };

    @Autowired
    public AuthenticationFilter(AuthenticationClient authenticationClient, UserService userService, UserDetailsService userDetailsService) {
        this.authenticationClient = authenticationClient;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Map<String, String> tokens;

        for (String uri : urisNotToCheck) {
            if (request.getRequestURI().contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            tokens = getTokensFromHeaders(request);
        } catch (TokenException e) {
            postException(e.getMessage(), response);
            return;
        }

        String access = tokens.get("access");
        String refresh = tokens.get("refresh");
        if (!authenticationClient.validateRefreshTokenFromRequest("Bearer_" + refresh).isValid()) {
            postException("Refresh token isn't valid, try to log in one more time", response);
            return;
        }

        if (!authenticationClient.validateAccessTokenFromRequest("Bearer_" + access).isValid()) {
            if (!authenticationClient.validateRefreshTokenFromRequest("Bearer_" + refresh).isValid()) {
                postException("Access and refresh tokens aren't valid, try to log in one more time", response);
                return;
            }

            String userEmail = authenticationClient.getEmailFromRefreshToken("Bearer_" + refresh).get("email");
            UserDTO userDTO = userService.getByEmail(userEmail);
            tokens = authenticationClient.updateTokensForUser(userDTO.getId());

            response.setHeader("Authorization_access", "Bearer_" + tokens.get("access"));
            response.setHeader("Authorization_refresh", "Bearer_" + tokens.get("refresh"));
        }

        String userEmail = authenticationClient.getEmailFromRefreshToken("Bearer_" + tokens.get("refresh")).get("email");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByUsername(userEmail),
                        null,
                        userDetailsService.loadUserByUsername(userEmail).getAuthorities()));
        filterChain.doFilter(request, response);
    }

    private Map<String, String> getTokensFromHeaders(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null");
        }

        String bearerWithAccessToken = request.getHeader("Authorization_access");
        String bearerWithRefreshToken = request.getHeader("Authorization_refresh");

        if (bearerWithAccessToken == null) {
            throw new TokenException("Authorization_access header not found");
        }
        if (!bearerWithAccessToken.startsWith("Bearer_")) {
            throw new TokenException("Access token should starts with Bearer_");
        }

        if (bearerWithRefreshToken == null) {
            throw new TokenException("Authorization_refresh header not found");
        }
        if (!bearerWithRefreshToken.startsWith("Bearer_")) {
            throw new TokenException("Refresh token should starts with Bearer_");
        }
        Map<String, String> res = new HashMap<>();
        res.put("access", bearerWithAccessToken.substring(7));
        res.put("refresh", bearerWithRefreshToken.substring(7));
        return res;
    }

    private void postException(String info, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setInfo(info);

        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDTO));
        response.getWriter().flush();
    }
}
