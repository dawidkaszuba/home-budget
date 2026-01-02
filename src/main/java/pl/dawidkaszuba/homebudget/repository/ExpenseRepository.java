package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.db.Home;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
    SELECT SUM(e.value)
    FROM Expense e
    WHERE e.account.home = :home
      AND e.createdAt BETWEEN :startDateTime AND :endDateTime
    """)
    Double getSumOfValueByHomeAndTimeBetween(
            @Param("home") Home home,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );


    @Query("SELECT e FROM Expense e WHERE e.account.home = :home")
    List<Expense> findAllByHome(@Param("home") Home home);

}
