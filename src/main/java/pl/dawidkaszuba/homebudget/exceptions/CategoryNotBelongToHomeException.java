package pl.dawidkaszuba.homebudget.exceptions;

public class CategoryNotBelongToHomeException extends RuntimeException {
    public CategoryNotBelongToHomeException(String message) {
        super(message);
    }
}
