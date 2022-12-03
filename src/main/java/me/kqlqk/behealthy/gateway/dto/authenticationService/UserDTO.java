package me.kqlqk.behealthy.gateway.dto.authenticationService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class UserDTO {
    public interface WithoutPasswordView {
    }

    @JsonView(WithoutPasswordView.class)
    private long id;

    @JsonView(WithoutPasswordView.class)
    private String name;

    @JsonView(WithoutPasswordView.class)
    private String email;

    private String password;
}
