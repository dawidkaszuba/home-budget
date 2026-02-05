package pl.dawidkaszuba.homebudget.exceptions;

public class IllegalUserStateException extends RuntimeException {
    public IllegalUserStateException(String message) {
        super(message);
    }
}
