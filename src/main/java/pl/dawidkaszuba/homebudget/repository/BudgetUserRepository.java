package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.BudgetUser;

import java.util.Optional;

public interface BudgetUserRepository extends JpaRepository<BudgetUser, Long> {
    Optional<BudgetUser> findByUserName(String username);
}
