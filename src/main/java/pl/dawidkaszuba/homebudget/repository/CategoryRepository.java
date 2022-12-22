package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
