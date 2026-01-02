package pl.dawidkaszuba.homebudget.model.dto.expense;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateExpenseDto {

    private Long categoryId;
    private Long accountId;
    private BigDecimal value;
}
