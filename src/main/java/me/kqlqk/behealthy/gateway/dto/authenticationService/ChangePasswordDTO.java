package me.kqlqk.behealthy.gateway.dto.authenticationService;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$",
            message = "Password must be between 8 and 50 characters, at least: 1 number, 1 uppercase letter, 1 lowercase letter")
    private String newPassword;
}
