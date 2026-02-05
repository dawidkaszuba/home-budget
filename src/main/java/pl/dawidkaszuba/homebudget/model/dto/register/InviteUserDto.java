package pl.dawidkaszuba.homebudget.model.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteUserDto {

    @NotBlank(message = "{invite.email.required}")
    @Email
    private String email;
    @NotBlank(message = "{invite.firstName.required}")
    @Size(min = 2, max = 50, message = "{user.firstName.size}")
    private String firstName;
    @NotBlank(message = "{invite.lastName.required}")
    @Size(min = 2, max = 50, message = "{user.lastName.size}")
    private String lastName;

}
