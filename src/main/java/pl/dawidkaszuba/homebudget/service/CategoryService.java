package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryViewDto;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;

import java.security.Principal;
import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories(Principal principal);

    List<Category> findByCategoryType(CategoryType type);

    void save(CreateCategoryDto dto, Principal principal);

    Category getCategoryById(Long id);

    void updateCategory(UpdateCategoryDto dto);
}
