package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.dawidkaszuba.homebudget.exceptions.AccountNotFoundException;
import pl.dawidkaszuba.homebudget.exceptions.CategoryNotBelongToHomeException;
import pl.dawidkaszuba.homebudget.exceptions.CategoryNotFoundException;
import pl.dawidkaszuba.homebudget.exceptions.ExpenseNotFoundException;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.*;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
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
    public Page<Expense> getAllExpensesByBudgetUser(String userName, Pageable pageable) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        Home userHome = budgetUser.getHome();
        return expenseRepository.findAllByHome(userHome, pageable);
    }

    @Transactional
    @Override
    public void save(CreateExpenseDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home userHome = budgetUser.getHome();

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.getHome().getId().equals(userHome.getId())) {
            throw new AccountNotFoundException("Account does not belong to the user's home");
        }
        if (!category.getHome().getId().equals(userHome.getId())) {
            throw new CategoryNotBelongToHomeException("Category does not belong to the user's home");
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
        if(!expense.getAccount().getId().equals(dto.getAccountId())) {
            Account account = accountRepository
                    .findById(dto.getAccountId())
                    .orElseThrow();
            expense.setAccount(account);
        }

        String note = StringUtils.hasText(dto.getNote()) ? dto.getNote() : null;
        if (!Objects.equals(note, expense.getNote())) {
            expense.setNote(note);
        }
        expense.setValue(dto.getValue());
        expense.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);
        return optional.orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getSumOfAllExpensesByUserAndTimeBetween(Principal principal,
                                                          LocalDateTime startDateTime,
                                                          LocalDateTime endDateTime) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return expenseRepository.getSumOfValueByHomeAndTimeBetween(home, startDateTime, endDateTime);
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getSumOfValueByHome(Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Home home = budgetUser.getHome();
        return expenseRepository.getSumOfValueByHome(home);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteIncome(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with id: " + id + " does not exist."));
        expenseRepository.delete(expense);
    }
}
