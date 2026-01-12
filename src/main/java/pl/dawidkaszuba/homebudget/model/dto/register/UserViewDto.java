package pl.dawidkaszuba.homebudget.model.dto.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserViewDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus status;

    public enum UserStatus {
        INVITED,
        ACTIVE,
        NOT_ACTIVE
    }
}
