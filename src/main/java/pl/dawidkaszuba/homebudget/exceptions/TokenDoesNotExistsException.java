package pl.dawidkaszuba.homebudget.exceptions;

public class TokenDoesNotExistsException extends RuntimeException {

    public TokenDoesNotExistsException(String message) {
        super(message);
    }
}
