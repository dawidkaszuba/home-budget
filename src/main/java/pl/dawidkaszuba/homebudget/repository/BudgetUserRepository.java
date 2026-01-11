package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;

import java.util.List;


public interface BudgetUserRepository extends JpaRepository<BudgetUser, Long> {

    @EntityGraph(attributePaths = "credentials")
    List<BudgetUser> findAllByHome(Home home);

}
