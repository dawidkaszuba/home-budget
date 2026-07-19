package pl.dawidkaszuba.homebudget.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DateValueDto {
    private LocalDateTime createdAt;
    private BigDecimal value;
}
