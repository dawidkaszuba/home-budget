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
    @Mapping(target = "accountName", source = "account.name")
    IncomeViewDto toViewDto(Income entity);

    @Mapping(target = "value", source = "value")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    Income toEntity(CreateIncomeDto dto);

    @Mapping(target = "categoryId", source = "category.id")
    UpdateIncomeDto toUpdateIncomeDto(Income income);
}
