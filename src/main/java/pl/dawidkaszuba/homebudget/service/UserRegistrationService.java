package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.dto.register.RegisterForm;

public interface UserRegistrationService {
    void registerUser(RegisterForm dto);
}
