package pl.dawidkaszuba.homebudget.exceptions;

public class AccountAlreadyExistsException extends RuntimeException implements FieldAwareException{

    public AccountAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "name";
    }

    @Override
    public String getCode() {
        return "account.alreadyExists";
    }
}
