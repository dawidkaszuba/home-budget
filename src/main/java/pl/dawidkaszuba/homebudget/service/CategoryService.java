package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.model.CategoryType;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<Category> getAllCategories();

    List<Category> findByCategoryType(CategoryType type);

    List<Category> getAllCategoriesByBudgetUser(String userName);

    Category save(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateExpense(Category category);
}
