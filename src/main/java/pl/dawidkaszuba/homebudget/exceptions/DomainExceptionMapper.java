package pl.dawidkaszuba.homebudget.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Slf4j
@Component
public class DomainExceptionMapper {

    public void map(RuntimeException e, BindingResult bindingResult) {

        if (e instanceof FieldAwareException fae) {
            bindingResult.rejectValue(
                    fae.getField(),
                    fae.getCode(),
                    e.getMessage()
            );
            log.error(e.getMessage());
            return;
        }

        if (e instanceof CategoryNotBelongToHomeException) {
            bindingResult.reject("access.denied", e.getMessage());
            return;
        }

        throw e;
    }
}
