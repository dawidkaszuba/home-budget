package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BudgetUserService budgetUserService;

    @Override
    public List<Account> findAllUserAccounts(Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return accountRepository.findAllByHome(home);
    }
}
