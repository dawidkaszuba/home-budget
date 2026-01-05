package pl.dawidkaszuba.homebudget.model.dto.income;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateIncomeDto {

    private Long id;
    private Long categoryId;
    private Long accountId;
    private BigDecimal value;
    @Size(max = 255)
    private String note;

}
