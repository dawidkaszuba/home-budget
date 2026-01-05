package pl.dawidkaszuba.homebudget.model.dto.expense;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateExpenseDto {

    private Long id;
    @NotNull(message = "Kategoria jest wymagana")
    private Long categoryId;
    @NotNull(message = "Konto jest wymagane")
    private Long accountId;
    @NotNull(message = "Kwota jest wymagana")
    @DecimalMin(value = "0.01", message = "Kwota musi być > 0")
    private BigDecimal value;
    @Size(max = 255)
    private String note;
}
