package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
