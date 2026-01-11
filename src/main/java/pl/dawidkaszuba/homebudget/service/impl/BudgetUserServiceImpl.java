package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BudgetUserServiceImpl implements BudgetUserService {

    private final UserCredentialRepository userCredentialRepository;
    private final BudgetUserRepository budgetUserRepository;


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

    @Override
    @Transactional(readOnly = true)
    public List<BudgetUser> getUsersForAdminHome(Principal principal) {

        BudgetUser admin = getBudgetUserByPrincipal(principal);

        if (!"ADMIN".equals(admin.getRole())) {
            throw new AccessDeniedException("Only ADMIN can view users");
        }

        return budgetUserRepository.findAllByHome(admin.getHome());
    }

}
