package pl.dawidkaszuba.homebudget.model.dto.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransferDto {

    @NotNull(message = "{transfer.sourceAccount.required}")
    private Long sourceAccountId;

    @NotNull(message = "{transfer.targetAccount.required}")
    private Long targetAccountId;

    @NotNull(message = "{transfer.value.required}")
    @DecimalMin(value = "0.01", message = "{transfer.value.min}")
    private BigDecimal value;

    @Size(max = 255, message = "{transfer.note.size}")
    private String note;

}
