package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.IllegalUserStateException;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        return budgetUserRepository.findAllByHomeOrderByFirstNameAscLastNameAsc(admin.getHome());
    }

    @Transactional
    @Override
    public void disableUser(Long userId, Principal principal) {
        BudgetUser admin = getBudgetUserByPrincipal(principal);

        if (!"ADMIN".equals(admin.getRole())) {
            throw new AccessDeniedException("Only ADMIN can disable users");
        }

        BudgetUser userToDisable = budgetUserRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!userToDisable.getHome().getId().equals(admin.getHome().getId())) {
            throw new AccessDeniedException("Cannot disable user from another home");
        }

        if (userToDisable.getId().equals(admin.getId())) {
            throw new IllegalStateException("Admin cannot disable himself");
        }

        userToDisable.getCredentials()
                .forEach(c -> c.setEnabled(false));

        log.info("User {} has been deactivated",
                userToDisable.getCredentials().stream()
                        .map(UserCredential::getEmail)
                        .collect(Collectors.joining(", ")));
    }

    @Transactional
    @Override
    public void enableUser(Long id, Principal principal) {
        BudgetUser admin = getBudgetUserByPrincipal(principal);

        BudgetUser user = budgetUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getHome().getId().equals(admin.getHome().getId())) {
            throw new AccessDeniedException("User not in your home");
        }

        UserCredential credential = user.getCredentials().stream()
                .filter(c -> c.getProvider() == AuthProvider.LOCAL)
                .findFirst()
                .orElseThrow();

        if (credential.isEnabled()) {
            return;
        }

        if (credential.getActivationToken() != null) {
            throw new IllegalUserStateException("Invited user must activate via email");
        }

        user.getCredentials()
                .forEach(c -> c.setEnabled(true));

        log.info("User {} has been reactivated",
                user.getCredentials().stream()
                        .map(UserCredential::getEmail)
                        .collect(Collectors.joining(", ")));
    }


}
