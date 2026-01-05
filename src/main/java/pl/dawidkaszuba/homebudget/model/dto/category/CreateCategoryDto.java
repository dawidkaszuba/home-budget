package pl.dawidkaszuba.homebudget.model.dto.category;

import lombok.Getter;
import lombok.Setter;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;

@Setter
@Getter
public class CreateCategoryDto {
    private Long id;
    private String name;
    private CategoryType categoryType;
}
