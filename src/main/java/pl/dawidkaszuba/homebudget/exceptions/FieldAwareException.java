package pl.dawidkaszuba.homebudget.exceptions;

public interface FieldAwareException {
    String getField();
    String getCode();
}
