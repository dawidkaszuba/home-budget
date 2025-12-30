package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.ExpenseViewDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ExpenseService {

    List<ExpenseViewDto> getAllExpensesByBudgetUser(String userName);

    void save(CreateExpenseDto dto, Principal principal);

    void updateExpense(UpdateExpenseDto dto);

    Expense getExpenseById(Long id);

    Double getSumOfAllExpensesByUserAndTimeBetween(BudgetUser userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void deleteIncome(Long id);
}
