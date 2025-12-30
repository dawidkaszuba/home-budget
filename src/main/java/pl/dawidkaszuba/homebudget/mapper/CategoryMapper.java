package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryViewDto;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryViewDto mapToDto(Category category);

    Category toEntity(CreateCategoryDto dto);
}
