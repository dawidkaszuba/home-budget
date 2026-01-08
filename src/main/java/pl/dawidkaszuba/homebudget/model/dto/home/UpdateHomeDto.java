package pl.dawidkaszuba.homebudget.model.dto.home;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateHomeDto {

    private Long id;
    @NotBlank(message = "{home.name.required}")
    @Size(max = 100, message = "{home.name.size}")
    private String name;
}
