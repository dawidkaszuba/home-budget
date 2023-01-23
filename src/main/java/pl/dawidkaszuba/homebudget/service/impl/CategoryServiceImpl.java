package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.model.CategoryType;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BudgetUserService budgetUserService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BudgetUserService budgetUserService) {
        this.categoryRepository = categoryRepository;
        this.budgetUserService = budgetUserService;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByCategoryType(CategoryType type) {
        return categoryRepository.findByCategoryType(type);
    }

    @Override
    public List<Category> getAllCategoriesByBudgetUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return categoryRepository.findAllByBudgetUser(budgetUser);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category updateExpense(Category category) {
        Category categoryInDb = categoryRepository.findById(category.getId()).get();
        categoryInDb.setCategoryType(category.getCategoryType());
        categoryInDb.setName(category.getName());
        categoryRepository.save(categoryInDb);
        return null;
    }

}
