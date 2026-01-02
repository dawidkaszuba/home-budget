package pl.dawidkaszuba.homebudget.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Summary {

    private BigDecimal balance;
    private BigDecimal annualBalance;
    private BigDecimal monthlyBalance;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlyIncomes;
}
