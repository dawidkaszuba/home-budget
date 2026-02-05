package pl.dawidkaszuba.homebudget.model.dto.category;

import java.math.BigDecimal;

public record CategoryAmountDto(
        String categoryName,
        BigDecimal amount) {}
