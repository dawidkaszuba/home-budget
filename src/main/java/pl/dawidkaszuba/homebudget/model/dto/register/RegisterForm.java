package pl.dawidkaszuba.homebudget.model.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {

    @NotBlank(message = "{user.firstName.required}")
    @Size(min = 2, max = 50, message = "{user.firstName.size}")
    private String firstName;

    @NotBlank(message = "{user.lastName.required}")
    @Size(min = 2, max = 50, message = "{user.lastName.size}")
    private String lastName;

    @NotBlank(message = "{user.email.required}")
    @Email
    private String email;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 8, max = 50, message = "{user.password.size}")
    private String password;

    @NotBlank(message = "{user.confirmedPassword.mismatch}")
    private String confirmedPassword;

    @NotBlank(message = "{user.homeName.required}")
    @Size(min = 3, max = 50, message = "{user.homeName.size}")
    private String homeName;
}

