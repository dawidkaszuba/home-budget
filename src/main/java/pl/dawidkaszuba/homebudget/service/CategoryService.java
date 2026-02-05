package pl.dawidkaszuba.homebudget.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;

import java.security.Principal;
import java.util.List;

@Service
public interface CategoryService {

    Page<Category> getAllCategories(Principal principal, Pageable pageable);

    List<Category> findByCategoryType(CategoryType type, Principal principal);

    void save(CreateCategoryDto dto, Principal principal);

    Category getCategoryById(Long id);

    void updateCategory(UpdateCategoryDto dto);
}
