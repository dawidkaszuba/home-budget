package pl.dawidkaszuba.homebudget.model;

import lombok.Getter;

@Getter
public enum CategoryType {
    INCOME("wpływ"), EXPENSE("wydatek");
    private String value;

    CategoryType(String value) {
        this.value = value;
    }
}
