package pl.dawidkaszuba.homebudget.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException implements FieldAwareException {

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "name";
    }

    @Override
    public String getCode() {
        return "category.alreadyExists";
    }
}
