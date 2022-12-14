package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
