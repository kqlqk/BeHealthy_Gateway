package unit.me.kqlqk.behealthy.gateway.cfg.filter;

import me.kqlqk.behealthy.gateway.cfg.filter.public_access.JWTFilterPublicAccess;
import me.kqlqk.behealthy.gateway.dto.ValidateDTO;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JWTFilterTest {
    @InjectMocks
    private JWTFilterPublicAccess jwtFilter;

    @Mock
    private FilterChain fc;

    @Mock
    private HttpServletRequest request;

    private final HttpServletResponse response = new MockHttpServletResponse();

    @Mock
    private AuthenticationClient authenticationClient;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private PrintWriter pw;

    @Test
    public void doFilterInternal_shouldAuthenticateUser() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/v1/users/1");
        when(request.getHeader("Authorization")).thenReturn("Bearer someJWTToken");
        ValidateDTO validateDTO = new ValidateDTO();
        validateDTO.setValid(true);
        when(authenticationClient.validateAccessToken(any())).thenReturn(validateDTO);
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", "user@mail.com");
        when(authenticationClient.getEmailFromAccessToken(any())).thenReturn(emailMap);
        when(userDetailsService.loadUserByUsername("user@mail.com")).thenReturn(userDetails);

        jwtFilter.doFilterInternalPublic(request, response, fc);
    }

    @Test
    public void doFilterInternal_ReturnExceptionBecauseInvalidHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/v1/users/1");
        when(request.getHeader("Authorization")).thenReturn("invalidToken");

        jwtFilter.doFilterInternalPublic(request, response, fc);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void doFilterInternal_ReturnExceptionBecauseInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/v1/users/1");
        when(request.getHeader("Authorization")).thenReturn("Bearer stillInvalidToken");
        when(authenticationClient.validateAccessToken(any())).thenThrow(new RuntimeException());

        jwtFilter.doFilterInternalPublic(request, response, fc);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void doFilterInternal_ReturnExceptionBecauseUnhandledException() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/v1/users/1");
        when(request.getHeader("Authorization")).thenReturn("Bearer stillInvalidToken");
        ValidateDTO validateDTO = new ValidateDTO();
        validateDTO.setValid(false);
        when(authenticationClient.validateAccessToken(any())).thenReturn(validateDTO);

        jwtFilter.doFilterInternalPublic(request, response, fc);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }
}
