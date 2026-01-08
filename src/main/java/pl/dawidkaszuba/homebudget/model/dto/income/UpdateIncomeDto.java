package pl.dawidkaszuba.homebudget.model.dto.income;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateIncomeDto {

    private Long id;
    @NotNull(message = "{income.category.required}")
    private Long categoryId;
    @NotNull(message = "{income.account.required}")
    private Long accountId;
    @NotNull(message = "{income.value.required}")
    @DecimalMin(value = "0.01", message = "{income.value.min}")
    private BigDecimal value;
    @Size(max = 255, message = "{income.note.size}")
    private String note;

}
