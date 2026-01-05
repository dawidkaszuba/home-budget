package pl.dawidkaszuba.homebudget.model.dto.account;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountDto {

    private Long id;
    private String name;
    @Size(max = 255)
    private String note;

}
