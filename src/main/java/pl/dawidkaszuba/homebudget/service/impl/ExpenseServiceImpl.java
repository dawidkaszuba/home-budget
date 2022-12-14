package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.util.List;

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
}
