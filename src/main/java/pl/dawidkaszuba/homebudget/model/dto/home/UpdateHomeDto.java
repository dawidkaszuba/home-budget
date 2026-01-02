package pl.dawidkaszuba.homebudget.model.dto.home;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateHomeDto {

    @NotBlank
    @Size(max = 100)
    private String name;
}
