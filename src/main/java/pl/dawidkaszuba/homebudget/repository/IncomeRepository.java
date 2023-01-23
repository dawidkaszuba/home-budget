package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Income;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query(value = "SELECT sum(i.value) FROM Income i WHERE i.budgetUser = ?1 and i.creationTime BETWEEN ?2 and ?3")
    Double findSumOfValueByUserAndCreateTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Income> findAllByBudgetUser(BudgetUser budgetUser);
}
