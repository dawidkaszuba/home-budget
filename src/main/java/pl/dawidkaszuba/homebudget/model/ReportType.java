package pl.dawidkaszuba.homebudget.model;

import lombok.Getter;

@Getter
public enum ReportType {
    INCOMES_REPORT("Raport wpływów"), EXPENSES_REPORT("Raport wydatków");
    private String name;

    ReportType(String name) {
        this.name = name;
    }
}
