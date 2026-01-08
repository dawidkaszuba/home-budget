package pl.dawidkaszuba.homebudget.model.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;

@Setter
@Getter
public class CreateCategoryDto {

    private Long id;
    @NotBlank(message = "{category.name.required}")
    private String name;
    @NotNull(message = "{category.categoryType.required}")
    private CategoryType categoryType;
    @Size(max = 255, message = "{category.note.size}")
    private String note;
}
