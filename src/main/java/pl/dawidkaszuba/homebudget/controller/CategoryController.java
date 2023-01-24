package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Category;
import pl.dawidkaszuba.homebudget.model.CategoryType;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final BudgetUserService budgetUserService;

    public CategoryController(CategoryService categoryService, BudgetUserService budgetUserService) {
        this.categoryService = categoryService;
        this.budgetUserService = budgetUserService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model, Principal principal) {
        model.addAttribute("categories", categoryService.getAllCategoriesByBudgetUser(principal.getName()));
        return "categories";
    }

    @GetMapping("/categories/new")
    public String addNewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("categoryTypes", CategoryType.values());
        return "create_category";
    }

    @PostMapping("/categories")
    public String saveExpense(@ModelAttribute("category") Category category, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        category.setBudgetUser(budgetUser);
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateExpense(@PathVariable Long id, Model model) {
        if(categoryService.getCategoryById(id).isPresent()) {
            model.addAttribute("category", categoryService.getCategoryById(id).get());
            model.addAttribute("categoryTypes", CategoryType.values());
            return "update_category";
        }
        return "redirect:categories"; //todo zrobić obsługę błędów
    }

    @PostMapping("/categories/{id}")
    public String saveUpdatedExpense(@PathVariable Long id, @ModelAttribute("category") Category category) {
        if(categoryService.getCategoryById(id).isPresent()) {
            categoryService.updateExpense(category);
            return "redirect:/categories";
        }
        //todo obsługa błędów
        return "/categories/edit/" + id;
    }
}
