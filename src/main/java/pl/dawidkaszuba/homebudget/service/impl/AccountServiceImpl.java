package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.AccountAlreadyExistsException;
import pl.dawidkaszuba.homebudget.exceptions.AccountNotFoundException;
import pl.dawidkaszuba.homebudget.model.db.*;
import pl.dawidkaszuba.homebudget.model.dto.account.AccountViewStateDto;
import pl.dawidkaszuba.homebudget.model.dto.account.CreateAccountDto;
import pl.dawidkaszuba.homebudget.model.dto.account.UpdateAccountDto;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BudgetUserService budgetUserService;

    @Transactional(readOnly = true)
    @Override
    public List<Account> findAllUserAccounts(Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return accountRepository.findAllByHome(home);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountViewStateDto> findAllUserAccountsWithState(Principal principal) {
        BudgetUser budgetUser = budgetUserService
                .getBudgetUserByUserName(principal.getName());

        return accountRepository
                .findAllAccountsWithStateByHome(budgetUser.getHome());
    }

    @Transactional
    @Override
    public void save(CreateAccountDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();

        if (accountRepository.existsByHomeAndNameIgnoreCase(home, dto.getName().trim())) {
            throw new AccountAlreadyExistsException("Account with name " + dto.getName() + " already exists for home: " + home.getName() + ".");
        }

        Account account = new Account();
        account.setName(dto.getName().trim());
        account.setHome(home);
        account.setOwner(budgetUser);

        accountRepository.save(account);
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public void updateAccount(UpdateAccountDto dto, Principal principal) {

        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();

        if (accountRepository.existsByHomeAndNameIgnoreCase(home, dto.getName().trim())) {
            throw new AccountAlreadyExistsException("Account with name " + dto.getName() + " already exists for home: " + home.getName() + ".");
        }

        Account account = accountRepository.findById(dto.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + dto.getId() + "does not exist."));
        String oldAccountName = account.getName();

        if (!account.getName().equals(dto.getName())) {
            account.setName(dto.getName());
            account.setUpdatedAt(LocalDateTime.now());
            log.info("User {} changed name of account {} to '{}'", principal.getName(), oldAccountName, dto.getName());
        }
    }
}
