package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Expense;

import java.util.List;
import java.util.Optional;

@Service
public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense save(Expense expense);

    Expense updateExpense(Expense expense);

    Optional<Expense> getExpenseById(Long id);
}
