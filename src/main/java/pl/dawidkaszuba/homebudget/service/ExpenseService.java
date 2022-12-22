package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Expense;

import java.util.List;

@Service
public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense save(Expense expense);
}
