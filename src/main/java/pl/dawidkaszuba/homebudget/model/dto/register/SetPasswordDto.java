package pl.dawidkaszuba.homebudget.model.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetPasswordDto {

    @NotBlank(message = "{user.password.required}")
    @Size(min = 8, max = 50, message = "{user.password.size}")
    private String password;

    @NotBlank(message = "{user.confirmedPassword.mismatch}")
    private String confirmedPassword;
}
