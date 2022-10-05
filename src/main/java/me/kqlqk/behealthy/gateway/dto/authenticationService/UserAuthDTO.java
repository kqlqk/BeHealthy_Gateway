package me.kqlqk.behealthy.gateway.dto.authenticationService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserAuthDTO {
    public interface WithoutPasswordView {
    }

    @JsonView(WithoutPasswordView.class)
    private long id;

    @Pattern(regexp = "[a-zA-Z]+", message = "Name should contains only letters")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 letters")
    @JsonView(WithoutPasswordView.class)
    private String name;

    @Pattern(regexp = "^[^\\s@]{3,}@[^\\s@]{2,}\\.[^\\s@]{2,}$", message = "Email should be valid")
    @JsonView(WithoutPasswordView.class)
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$",
            message = "Password must be between 8 and 50 characters, at least: 1 number, 1 uppercase letter, 1 lowercase letter")
    private String password;
}
