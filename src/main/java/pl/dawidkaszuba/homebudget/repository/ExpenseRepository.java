package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
    SELECT SUM(e.value)
    FROM Expense e
    WHERE e.account.home = :home
      AND e.createdAt BETWEEN :startDateTime AND :endDateTime
    """)
    BigDecimal getSumOfValueByHomeAndTimeBetween(
            @Param("home") Home home,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query("""
    SELECT SUM(e.value)
    FROM Expense e
    WHERE e.account.home = :home
    """)
    BigDecimal getSumOfValueByHome(@Param("home") Home home);


    @Query("SELECT e FROM Expense e WHERE e.account.home = :home ORDER BY e.createdAt desc")
    Page<Expense> findAllByHome(@Param("home") Home home, Pageable pageable);


    @Query("""
        SELECT new pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto(c.name, SUM(e.value))
        FROM Expense e
        JOIN e.category c
        WHERE e.account.home = :home
        AND e.createdAt >= :from AND e.createdAt <= :to
        GROUP BY c
        ORDER BY SUM(e.value) DESC
        """)
    List<CategoryAmountDto> findAllByHomeGroupedByCategory(@Param("home") Home home, @Param("from")  LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("""
            SELECT new pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto(
                c.name,
                SUM(e.value)
            )
            FROM Expense e
            JOIN e.category c
            WHERE (:categoryIds IS NULL OR c.id IN :categoryIds)
              AND e.createdAt BETWEEN :from AND :to
              AND e.account.home = :home
            GROUP BY c
        """)
    List<ReportRowDto> findForReport(@Param("home") Home home,
                                     @Param("from")  LocalDateTime from,
                                     @Param("to") LocalDateTime to,
                                     @Param("categoryIds") List<Long> categoryIds);

    @Query("""
            SELECT new pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto(
                c.name,
                SUM(e.value)
            )
            FROM Expense e
            JOIN e.category c
            WHERE e.createdAt BETWEEN :from AND :to
              AND e.account.home = :home
            GROUP BY c.name
        """)
    List<ReportRowDto> findForReport(@Param("home")  Home home,
                                     @Param("from") LocalDateTime from,
                                     @Param("to") LocalDateTime to);

}
