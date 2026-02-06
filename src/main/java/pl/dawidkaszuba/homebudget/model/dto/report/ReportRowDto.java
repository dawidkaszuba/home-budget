package pl.dawidkaszuba.homebudget.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ReportRowDto {
    private String categoryName;
    private BigDecimal amount;
}
