package pl.dawidkaszuba.homebudget.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.YearMonth;

@AllArgsConstructor
@Getter
public class MonthlyNetDto {
    private YearMonth month;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal net;
}
