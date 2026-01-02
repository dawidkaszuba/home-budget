package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

    private final BudgetUserRepository budgetUserRepository;

    public HomeServiceImpl(BudgetUserRepository budgetUserRepository) {
        this.budgetUserRepository = budgetUserRepository;
    }

    @Override
    public Home getHomeByBudgetUser(String userName) {
        return budgetUserRepository.findByUserName(userName)
                .map(BudgetUser::getHome)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found: " + userName)
                );
    }
}
