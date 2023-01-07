package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Expense;

import java.time.LocalDateTime;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value = "SELECT sum(e.value) FROM Expense e WHERE e.budgetUser = ?1 and e.creationTime BETWEEN ?2 and ?3")
    Double getSumOfValueByUserAndTimeBetween(BudgetUser user, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
