package pl.dawidkaszuba.homebudget.model.dto.expense;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateExpenseDto {

    private Long id;
    @NotNull(message = "{expense.category.required}")
    private Long categoryId;
    @NotNull(message = "{expense.account.required}")
    private Long accountId;
    @NotNull(message = "{expense.value.required}")
    @DecimalMin(value = "0.01", message = "{expense.value.min}")
    private BigDecimal value;
    @Size(max = 255, message = "{expense.note.size}")
    private String note;

}
