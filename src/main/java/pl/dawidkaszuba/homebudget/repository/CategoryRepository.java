package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.model.CategoryType;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryType(CategoryType type);

    List<Category> findAllByBudgetUser(BudgetUser budgetUser);
}

