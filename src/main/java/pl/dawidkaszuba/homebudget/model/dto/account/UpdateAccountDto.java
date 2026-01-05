package pl.dawidkaszuba.homebudget.model.dto.account;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountDto {
    private Long id;
    private String name;
    @Size(max = 255)
    private String note;
}
