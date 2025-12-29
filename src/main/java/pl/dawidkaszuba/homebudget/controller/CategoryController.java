package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;

import java.security.Principal;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final BudgetUserService budgetUserService;

    public CategoryController(CategoryService categoryService, BudgetUserService budgetUserService) {
        this.categoryService = categoryService;
        this.budgetUserService = budgetUserService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/categories/new")
    public String addNewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("budgetUsers", budgetUserService.getAllUsers());
        return "create_category";
    }

    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute("category") Category category, Principal principal) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {
        if(categoryService.getCategoryById(id).isPresent()) {
            model.addAttribute("category", categoryService.getCategoryById(id).get());
            model.addAttribute("categoryTypes", CategoryType.values());
            model.addAttribute("budgetUsers", budgetUserService.getAllUsers());
            return "update_category";
        }
        return "redirect:categories"; //todo zrobić obsługę błędów
    }

    @PostMapping("/categories/{id}")
    public String saveUpdatedCategory(@PathVariable Long id, @ModelAttribute("category") Category category) {
        if(categoryService.getCategoryById(id).isPresent()) {
            categoryService.updateCategory(category);
            return "redirect:/categories";
        }
        //todo obsługa błędów
        return "/categories/edit/" + id;
    }
}
