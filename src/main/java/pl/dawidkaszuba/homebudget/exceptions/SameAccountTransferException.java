package pl.dawidkaszuba.homebudget.exceptions;

public class SameAccountTransferException extends RuntimeException implements FieldAwareException {

    public SameAccountTransferException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "targetAccountId";
    }

    @Override
    public String getCode() {
        return "transfer.sameAccount";
    }

}
