package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.ExpenseViewDto;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "accountName", source = "account.name")
    ExpenseViewDto toDto(Expense entity);

    @Mapping(target = "value", source = "value")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    Expense toEntity(CreateExpenseDto createExpenseDto);

    @Mapping(target = "categoryId", source = "category.id")
    UpdateExpenseDto toUpdateExpenseDto(Expense expense);
}
