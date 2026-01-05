package pl.dawidkaszuba.homebudget.exceptions;

public class IncomeNotFoundException extends RuntimeException implements FieldAwareException {
    public IncomeNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "id";
    }

    @Override
    public String getCode() {
        return "income.notFound";
    }
}
