package pl.dawidkaszuba.homebudget.model.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountDto {

    private Long id;
    @NotBlank(message = "{account.name.required}")
    private String name;
    @Size(max = 255, message = "{account.note.size}")
    private String note;

}
