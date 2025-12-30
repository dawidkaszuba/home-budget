package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.IncomeViewDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mapping(target = "categoryName", source = "category.name")
    IncomeViewDto toDto(Income entity);

    @Mapping(target = "value", source = "value")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "budgetUser", ignore = true)
    Income toEntity(CreateIncomeDto dto);

    @Mapping(target = "categoryId", source = "category.id")
    UpdateIncomeDto toUpdateIncomeDto(Income income);
}
