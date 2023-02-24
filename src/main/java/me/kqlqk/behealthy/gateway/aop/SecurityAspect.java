package me.kqlqk.behealthy.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.authentication_service.AccessTokenDTO;
import me.kqlqk.behealthy.gateway.dto.authentication_service.GetUserDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
        Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();

        for (int i = 0; i < m.getParameters().length; i++) {
            if (m.getParameters()[i].getName().equals("id")) {
                requestUserId = (long) joinPoint.getArgs()[i];
                break;
            }
        }

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(getTokenFromRequest(request));
        String email = authenticationClient.getEmailFromAccessToken(accessTokenDTO).get("email");
        GetUserDTO userDTO = authenticationClient.getUserByEmail(email);

        if (userDTO.getId() != requestUserId) {
            log.info(userDTO.getEmail() + " failed access to " + request.getRequestURI() + " (securityAspect)");

            throw new RuntimeException("Wrong id! Your id = " + userDTO.getId());
        }
        else {
            log.info(userDTO.getEmail() + " got access to " + request.getRequestURI() + " (securityAspect)");
        }
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
}
