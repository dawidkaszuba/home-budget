package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.model.BudgetUser;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface IncomeService {
    List<Income> getAllIncomes();
    Optional<Income> findById(Long id);

    Income save(Income expense);

    Optional<Income> getIncomeById(Long id);

    Income updateIncome(Income income);

    Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
