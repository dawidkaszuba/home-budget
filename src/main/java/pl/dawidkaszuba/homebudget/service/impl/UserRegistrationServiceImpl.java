package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.register.RegisterForm;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.repository.HomeRepository;
import pl.dawidkaszuba.homebudget.service.UserRegistrationService;

@RequiredArgsConstructor
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private static final String ADMIN_ROLE = "ADMIN";

    private final HomeRepository homeRepository;
    private final BudgetUserRepository budgetUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public void registerUser(RegisterForm dto) {

        Home newHome = new Home();
        newHome.setName(dto.getHomeName());
        Home home = homeRepository.save(newHome);

        BudgetUser user = new BudgetUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setRole(ADMIN_ROLE);
        user.setHome(home);

        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        budgetUserRepository.save(user);
    }
}