package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("""
    SELECT SUM(i.value)
    FROM Income i
    WHERE i.account.home = :home
      AND i.createdAt BETWEEN :startDateTime AND :endDateTime
    """)
    BigDecimal findSumOfValueByUserAndCreateTimeBetween(
            @Param("home") Home home,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query("""
    SELECT SUM(i.value)
    FROM Income i
    WHERE i.account.home = :home
    """)
    BigDecimal findSumOfValueByHome(@Param("home") Home home);


    @Query("SELECT i FROM Income i WHERE i.account.home = :home ORDER BY i.createdAt DESC")
    Page<Income> findAllByHome(@Param("home") Home home, Pageable pageable);

    @Query("""
        SELECT new pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto(c.name, SUM(i.value))
        FROM Income i
        JOIN i.category c
        WHERE i.account.home = :home
        AND i.createdAt >= :from AND i.createdAt <= :to
        GROUP BY c
        ORDER BY SUM(i.value) DESC
        """)
    List<CategoryAmountDto> findSumIncomesByCategory(Home home, LocalDateTime from, LocalDateTime to);

    @Query("""
            SELECT new pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto(
                c.name,
                SUM(i.value)
            )
            FROM Income i
            JOIN i.category c
            WHERE (:categoryIds IS NULL OR c.id IN :categoryIds)
              AND i.createdAt BETWEEN :from AND :to
              AND i.account.home = :home
            GROUP BY c
        """)
    List<ReportRowDto> findForReport(@Param("home") Home home,
                                     @Param("from")  LocalDateTime from,
                                     @Param("to") LocalDateTime to,
                                     @Param("categoryIds") List<Long> categoryIds);

    @Query("""
            SELECT new pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto(
                c.name,
                SUM(i.value)
            )
            FROM Income i
            JOIN i.category c
            WHERE i.createdAt BETWEEN :from AND :to
              AND i.account.home = :home
            GROUP BY c.name
        """)
    List<ReportRowDto> findForReport(
            Home home,
            LocalDateTime from,
            LocalDateTime to);
}
