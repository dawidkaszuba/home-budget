package pl.dawidkaszuba.homebudget.model.dto.income;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateIncomeDto {

    private Long id;
    private Long categoryId;
    private BigDecimal value;

}
