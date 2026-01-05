package pl.dawidkaszuba.homebudget.exceptions;

public class ExpenseNotFoundException extends RuntimeException implements FieldAwareException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "id";
    }

    @Override
    public String getCode() {
        return "expense.notFound";
    }
}
