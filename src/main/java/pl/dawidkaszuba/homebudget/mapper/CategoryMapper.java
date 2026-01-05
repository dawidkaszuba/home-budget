package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryViewDto;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryViewDto mapToViewDto(Category category);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "home", ignore = true)
    Category toEntity(CreateCategoryDto dto);

    UpdateCategoryDto toUpdateCategoryDto(Category category);
}
