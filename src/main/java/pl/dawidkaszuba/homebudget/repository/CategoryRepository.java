package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Home;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryTypeAndHomeOrderByName(CategoryType type, Home home);

    boolean existsByHomeAndCategoryTypeAndName(Home home, CategoryType categoryType, String name);

    Page<Category> findAllByHomeOrderByName(Home home, Pageable pageable);
}

