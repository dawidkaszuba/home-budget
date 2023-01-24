package pl.dawidkaszuba.homebudget.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesRestController {

    private CategoryService categoryService;

    public CategoriesRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
