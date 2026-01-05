package pl.dawidkaszuba.homebudget.exceptions;

public class BudgetUserNotFoundException extends RuntimeException {
    public BudgetUserNotFoundException(String message) {
        super(message);
    }
}
