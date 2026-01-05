package pl.dawidkaszuba.homebudget.exceptions;

public class CategoryNotFoundException extends RuntimeException implements FieldAwareException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getField() {
        return "id";
    }

    @Override
    public String getCode() {
        return "category.notFound";
    }
}
