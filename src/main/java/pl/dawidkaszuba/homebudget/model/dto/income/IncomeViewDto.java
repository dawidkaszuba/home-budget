package pl.dawidkaszuba.homebudget.model.dto.income;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IncomeViewDto {

    private Long id;
    private String categoryName;
    private BigDecimal value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
