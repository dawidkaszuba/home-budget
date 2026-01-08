package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.db.Income;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
}
