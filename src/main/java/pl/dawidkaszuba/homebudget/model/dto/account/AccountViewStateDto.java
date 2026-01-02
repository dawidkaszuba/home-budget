package pl.dawidkaszuba.homebudget.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AccountViewStateDto {
    private String name;
    private BigDecimal state;
}
