package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

@Service
public class BudgetUserServiceImpl implements BudgetUserService {

    private final BudgetUserRepository budgetUserRepository;

    public BudgetUserServiceImpl(BudgetUserRepository budgetUserRepository) {
        this.budgetUserRepository = budgetUserRepository;
    }

    @Override
    public BudgetUser getBudgetUserByUserName(String userName) {
       return budgetUserRepository
           .findByUserName(userName)
           .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
    }
}
