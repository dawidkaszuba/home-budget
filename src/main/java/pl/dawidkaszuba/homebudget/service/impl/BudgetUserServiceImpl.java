package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class BudgetUserServiceImpl implements BudgetUserService {

    private final UserCredentialRepository userCredentialRepository;


    @Transactional(readOnly = true)
    @Override
    public BudgetUser getBudgetUserByPrincipal(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new UsernameNotFoundException("No authenticated user found");
        }

        return userCredentialRepository
                .findByProviderAndEmail(AuthProvider.LOCAL, principal.getName())
                .map(UserCredential::getUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found for email: " + principal.getName())
                );
    }

}
