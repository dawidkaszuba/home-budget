package pl.dawidkaszuba.homebudget.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ExpenseService {

    Page<Expense> getAllExpensesByBudgetUser(Principal principal, Pageable pageable);

    void save(CreateExpenseDto dto, Principal principal);

    void updateExpense(UpdateExpenseDto dto);

    Expense getExpenseById(Long id);

    BigDecimal getSumOfAllExpensesByUserAndTimeBetween(Principal principal, LocalDateTime startDateTime, LocalDateTime endDateTime);

    BigDecimal getSumOfValueByHome(Principal principal);

    void deleteIncome(Long id);

    List<CategoryAmountDto> getAllExpensesByHomeAndCategory(Principal principal, LocalDateTime from, LocalDateTime to);
}
