package pl.dawidkaszuba.homebudget.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class DomainExceptionMapper {

    public void map(RuntimeException e, BindingResult bindingResult) {

        if (e instanceof FieldAwareException fae) {
            bindingResult.rejectValue(
                    fae.getField(),
                    fae.getCode(),
                    e.getMessage()
            );
            return;
        }

        if (e instanceof CategoryNotBelongToHomeException) {
            bindingResult.reject("access.denied", e.getMessage());
            return;
        }

        throw e;
    }
}
