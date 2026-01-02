package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.db.Home;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByHome(Home home);
}
