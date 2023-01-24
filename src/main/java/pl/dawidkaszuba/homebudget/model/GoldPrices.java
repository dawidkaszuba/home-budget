package pl.dawidkaszuba.homebudget.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GoldPrices {

    private List<GoldPrice> goldPrices;
}
