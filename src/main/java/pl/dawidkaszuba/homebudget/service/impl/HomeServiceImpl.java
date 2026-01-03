package pl.dawidkaszuba.homebudget.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.BudgetUserNotFoundException;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    private final BudgetUserRepository budgetUserRepository;

    public HomeServiceImpl(BudgetUserRepository budgetUserRepository) {
        this.budgetUserRepository = budgetUserRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Home getHomeByBudgetUser(String userName) {
        return budgetUserRepository.findByUserName(userName)
                .map(BudgetUser::getHome)
                .orElseThrow(() ->
                    new BudgetUserNotFoundException("User not found: " + userName)
                );
    }

    @Transactional
    @Override
    public void updateName(String name, Principal principal) {
        Home home = getHomeByBudgetUser(principal.getName());

        if (!name.equals(home.getName())) {
            home.setName(name);
            log.info("User {} changed home's name to '{}'", principal.getName(), name);
        }
    }
}
