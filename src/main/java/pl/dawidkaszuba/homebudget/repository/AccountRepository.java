package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.account.AccountViewStateDto;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByHome(Home home);

    @Query("""
        SELECT new pl.dawidkaszuba.homebudget.model.dto.account.AccountViewStateDto(
            a.name,
            COALESCE(SUM(i.value), 0) - COALESCE(SUM(e.value), 0)
        )
        FROM Account a
        LEFT JOIN Income i ON i.account = a AND i.deletedAt IS NULL
        LEFT JOIN Expense e ON e.account = a AND e.deletedAt IS NULL
        WHERE a.home = :home
        AND a.deletedAt IS NULL
        GROUP BY a.id, a.name
    """)
    List<AccountViewStateDto> findAllAccountsWithStateByHome(@Param("home") Home home);

}
