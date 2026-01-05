package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.IncomeNotFoundException;
import pl.dawidkaszuba.homebudget.mapper.IncomeMapper;
import pl.dawidkaszuba.homebudget.model.db.*;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.repository.IncomeRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final BudgetUserService budgetUserService;
    private final CategoryRepository categoryRepository;
    private final IncomeMapper incomeMapper;
    private final AccountRepository accountRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Income> getAllIncomesByUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        Home userHome = budgetUser.getHome();
        return incomeRepository.findAllByHome(userHome);
    }


    @Transactional
    @Override
    public void save(CreateIncomeDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home userHome = budgetUser.getHome();

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getHome().getId().equals(userHome.getId())) {
            throw new IllegalStateException("Account does not belong to the user's home");
        }
        if (!category.getHome().getId().equals(userHome.getId())) {
            throw new IllegalStateException("Category does not belong to the user's home");
        }

        Income income = incomeMapper.toEntity(dto);
        income.setCategory(category);
        income.setAccount(account);
        incomeRepository.save(income);
    }

    @Transactional(readOnly = true)
    @Override
    public Income getIncomeById(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        return optionalIncome.orElseThrow();
    }

    @Transactional
    @Override
    public void updateIncome(UpdateIncomeDto dto) {

        Income income = incomeRepository
                .findById(dto.getId())
                .orElseThrow();

        if(!income.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryRepository
                    .findById(dto.getCategoryId())
                    .orElseThrow();
            income.setCategory(category);
        }

        if(!income.getAccount().getId().equals(dto.getAccountId())) {
            Account account = accountRepository
                    .findById(dto.getAccountId())
                    .orElseThrow();
            income.setAccount(account);
        }

        income.setValue(dto.getValue());
        income.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getSumOfAllIncomesByUserAndTimeBetween(LocalDateTime startDateTime,
                                                         LocalDateTime endDateTime,
                                                         Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return incomeRepository.findSumOfValueByUserAndCreateTimeBetween(home, startDateTime, endDateTime);
    }

    @Transactional
    @Override
    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income with id: " + id + " does not exist."));
        incomeRepository.delete(income);
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getSumOfValueByHome(Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return incomeRepository.findSumOfValueByHome(home);
    }
}
