package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.*;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.ExpenseViewDto;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetUserService budgetUserService;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;
    private final AccountRepository accountRepository;



    @Transactional(readOnly = true)
    @Override
    public List<ExpenseViewDto> getAllExpensesByBudgetUserHome(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        Home userHome = budgetUser.getHome();
        return expenseRepository.findAllByHome(userHome)
                .stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void save(CreateExpenseDto dto, Principal principal) {
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

        Expense expense = expenseMapper.toEntity(dto);
        expense.setCategory(category);
        expense.setAccount(account);

        expenseRepository.save(expense);
    }

    @Transactional
    @Override
    public void updateExpense(UpdateExpenseDto dto) {

        Expense expense = expenseRepository
                .findById(dto.getId())
                .orElseThrow();

        if (!expense.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryRepository
                    .findById(dto.getCategoryId())
                    .orElseThrow();
            expense.setCategory(category);
        }
        expense.setValue(dto.getValue());
        expense.setUpdatedAt(LocalDateTime.now());
        //todo  Brak save() – Hibernate zrobi dirty checking - wrzucić do lesson learned
    }

    @Transactional(readOnly = true)
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);
        return optional.orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Double getSumOfAllExpensesByUserAndTimeBetween(Principal principal,
                                                          LocalDateTime startDateTime,
                                                          LocalDateTime endDateTime) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return expenseRepository.getSumOfValueByHomeAndTimeBetween(home, startDateTime, endDateTime);
    }

    @Transactional
    @Override
    public void deleteIncome(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expenseRepository.delete(expense);
    }
}
