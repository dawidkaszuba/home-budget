package pl.dawidkaszuba.homebudget.model.dto.category;

import lombok.Getter;
import lombok.Setter;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;

@Getter
@Setter
public class UpdateCategoryDto {

    private Long id;
    private String name;
    private CategoryType categoryType;
}
