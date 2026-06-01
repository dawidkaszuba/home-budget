package pl.dawidkaszuba.homebudget.model.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransferViewDto {

    private Long id;
    private String sourceAccountName;
    private String targetAccountName;
    private BigDecimal value;
    private String note;
    private LocalDateTime createdAt;

}
