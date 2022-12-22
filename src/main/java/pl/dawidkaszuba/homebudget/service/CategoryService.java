package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.model.CategoryType;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories();

    List<Category> findByCategoryType(CategoryType type);

}
