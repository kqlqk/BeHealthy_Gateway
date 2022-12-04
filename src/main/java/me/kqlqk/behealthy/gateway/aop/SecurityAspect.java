package me.kqlqk.behealthy.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.authenticationService.TokensDTO;
import me.kqlqk.behealthy.gateway.dto.authenticationService.UserDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService.TokenNotFoundException;
import me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SecurityAspect {
    private final AuthenticationClient authenticationClient;
    private final HttpServletRequest request;

    @Autowired
    public SecurityAspect(AuthenticationClient authenticationClient, HttpServletRequest request) {
        this.authenticationClient = authenticationClient;
        this.request = request;
    }

    @Before("@annotation(CheckUserId)")
    private void beforeCheckUserIdAnnotation(JoinPoint joinPoint) {
        long requestUserId = 0;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                requestUserId = (long) arg;
            }
        }

        String accessToken = getTokenFromRequest(request);
        String email = authenticationClient.getEmailFromAccessToken(new TokensDTO(accessToken, null)).get("email");
        UserDTO userDTO = authenticationClient.getUserByEmail(email);

        if (userDTO.getId() != requestUserId) {
            log.info(userDTO.getEmail() + " failed access to " + request.getRequestURI() + " (securityAspect)");

            throw new UserException("Wrong id! Your id = " + userDTO.getId());
        } else {
            log.info(userDTO.getEmail() + " got access to " + request.getRequestURI() + " (securityAspect)");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
            throw new TokenNotFoundException("Bearer token not found");
        }
        throw new TokenNotFoundException("Authorization header not found");
    }
}
