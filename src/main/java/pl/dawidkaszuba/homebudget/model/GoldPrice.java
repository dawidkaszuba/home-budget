package pl.dawidkaszuba.homebudget.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GoldPrice {
    private double cena;
    private LocalDate data;
}
