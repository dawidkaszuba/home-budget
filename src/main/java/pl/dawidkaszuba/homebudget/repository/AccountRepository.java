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
                    (
                        SELECT COALESCE(SUM(i.value), 0)
                        FROM Income i
                        WHERE i.account = a
                          AND i.deletedAt IS NULL
                    ) -
                    (
                        SELECT COALESCE(SUM(e.value), 0)
                        FROM Expense e
                        WHERE e.account = a
                          AND e.deletedAt IS NULL
                    )
                )
                FROM Account a
                WHERE a.home = :home
                  AND a.deletedAt IS NULL
    """)
    List<AccountViewStateDto> findAllAccountsWithStateByHome(@Param("home") Home home);

    boolean existsByHomeAndNameIgnoreCase(Home home, String name);

}
