package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.BudgetUserNotFoundException;
import pl.dawidkaszuba.homebudget.exceptions.IllegalUserStateException;
import pl.dawidkaszuba.homebudget.exceptions.UserAlreadyExistsException;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.model.dto.register.InviteUserDto;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.EmailService;
import pl.dawidkaszuba.homebudget.service.InvitationUserService;

import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvitationUserServiceImpl implements InvitationUserService {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    private final UserCredentialRepository userCredentialRepository;
    private final BudgetUserRepository budgetUserRepository;
    private final BudgetUserService budgetUserService;
    private final EmailService emailService;

    @Transactional
    @Override
    public UserCredential inviteUserToHome(InviteUserDto dto, Principal principal) {

        BudgetUser admin = budgetUserService.getBudgetUserByPrincipal(principal);

        if (!ADMIN_ROLE.equals(admin.getRole())) {
            throw new AccessDeniedException("Only ADMIN can invite users to this home");
        }

        Home home = admin.getHome();

        if (userCredentialRepository.findByProviderAndEmail(AuthProvider.LOCAL, dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + dto.getEmail() + " already exists");
        }

        BudgetUser newUser = new BudgetUser();
        newUser.setFirstName(dto.getFirstName());
        newUser.setLastName(dto.getLastName());
        newUser.setRole(USER_ROLE);
        newUser.setHome(home);

        UserCredential credential = new UserCredential();
        credential.setProvider(AuthProvider.LOCAL);
        credential.setEmail(dto.getEmail());
        credential.setEnabled(false);

        credential.setActivationToken(UUID.randomUUID().toString());

        newUser.addCredential(credential);

        budgetUserRepository.save(newUser);

        emailService.sendActivationEmail(credential);

        return credential;
    }

    @Override
    @Transactional
    public void resendInvitation(Long userId) {
        BudgetUser user = budgetUserRepository.findById(userId)
                .orElseThrow(() -> new BudgetUserNotFoundException("User not found"));

        UserCredential credential = user.getCredentials().stream()
                .filter(c -> c.getProvider() == AuthProvider.LOCAL)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("LOCAL credential not found"));

        if (credential.isEnabled()) {
            throw new IllegalUserStateException("User is already active");
        }

        credential.setActivationToken(UUID.randomUUID().toString());

        emailService.sendActivationEmail(credential);
    }


}
