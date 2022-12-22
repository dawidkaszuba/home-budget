package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
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
        expense.setLastEditTime(LocalDateTime.now());
        expense.setTime(expenseFromDb.getTime());
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
}
