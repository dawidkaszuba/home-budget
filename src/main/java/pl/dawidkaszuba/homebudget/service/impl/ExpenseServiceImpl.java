package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetUserService budgetUserService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, BudgetUserService budgetUserService) {
        this.expenseRepository = expenseRepository;
        this.budgetUserService = budgetUserService;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getAllExpensesByBudgetUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return expenseRepository.findAllByBudgetUser(budgetUser);
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense expenseFromDb = getExpenseById(expense.getId()).get();
        expenseFromDb.setCategory(expense.getCategory());
        expenseFromDb.setValue(expense.getValue());
        expenseFromDb.setLastEditTime(LocalDateTime.now());
        expenseFromDb.setCreationTime(expenseFromDb.getCreationTime());
        return expenseRepository.save(expenseFromDb);
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Double getSumOfAllExpensesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return expenseRepository.getSumOfValueByUserAndTimeBetween(budgetUser, startDateTime, endDateTime);
    }
}
