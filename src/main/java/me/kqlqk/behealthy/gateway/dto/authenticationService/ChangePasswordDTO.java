package me.kqlqk.behealthy.gateway.dto.authenticationService;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordDTO {
    @NotEmpty(message = "OldPassword cannot be null")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$",
            message = "newPassword must be between 8 and 50 characters, at least: 1 number, 1 uppercase letter, 1 lowercase letter")
    @NotEmpty(message = "NewPassword cannot be null")
    private String newPassword;
}
