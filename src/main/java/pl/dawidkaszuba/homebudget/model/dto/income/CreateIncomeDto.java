package pl.dawidkaszuba.homebudget.model.dto.income;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateIncomeDto {

    private Long categoryId;
    private Long accountId;
    private BigDecimal value;
}
