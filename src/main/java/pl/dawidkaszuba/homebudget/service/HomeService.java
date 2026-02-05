package pl.dawidkaszuba.homebudget.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pl.dawidkaszuba.homebudget.model.db.Home;

import java.security.Principal;

public interface HomeService {

    Home getHomeByPrincipal(Principal principal);

    void updateName(@NotBlank @Size(max = 100) String name, Principal principal);
}
