package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.ExpenseViewDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ExpenseService {

    List<ExpenseViewDto> getAllExpensesByBudgetUserHome(String userName);

    void save(CreateExpenseDto dto, Principal principal);

    void updateExpense(UpdateExpenseDto dto);

    Expense getExpenseById(Long id);

    BigDecimal getSumOfAllExpensesByUserAndTimeBetween(Principal principal, LocalDateTime startDateTime, LocalDateTime endDateTime);

    BigDecimal getSumOfValueByHome(Principal principal);

    void deleteIncome(Long id);
}
