package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.db.Income;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("""
    SELECT SUM(i.value)
    FROM Income i
    WHERE i.account.home = :home
      AND i.createdAt BETWEEN :startDateTime AND :endDateTime
    """)
    Double findSumOfValueByUserAndCreateTimeBetween(
            @Param("home") Home home,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query("SELECT i FROM Income i WHERE i.account.home = :home")
    List<Income> findAllByHome(@Param("home") Home home);

    @Query("""
    SELECT SUM(i.value)
    FROM Income i
    WHERE i.account.home = :home
    """)
    Double findSumOfValueByHome(@Param("home") Home home);
}
