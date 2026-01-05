package pl.dawidkaszuba.homebudget.model.dto.expense;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateExpenseDto {

    private Long id;
    private Long categoryId;
    private Long accountId;
    private BigDecimal value;

}
