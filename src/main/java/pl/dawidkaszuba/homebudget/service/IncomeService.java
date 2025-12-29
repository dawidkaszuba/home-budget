package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface IncomeService {
    List<Income> getAllIncomesByUser(String userName);
    Optional<Income> findById(Long id);

    Income save(Income expense);

    Optional<Income> getIncomeById(Long id);

    Income updateIncome(Income income);

    Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void deleteIncome(Income income);
}
