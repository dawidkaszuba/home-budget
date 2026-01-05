package pl.dawidkaszuba.homebudget.exceptions;

public class AccountNotFoundException extends RuntimeException implements FieldAwareException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "accountId";
    }

    @Override
    public String getCode() {
        return "account.notFound";
    }
}
