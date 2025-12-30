package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;
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
        CreateCategoryDto dto = new CreateCategoryDto();
        model.addAttribute("category", dto);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("budgetUsers", budgetUserService.getAllUsers()); //todo to remove??
        return "create_category";
    }

    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute("category") CreateCategoryDto dto, Principal principal) {
        categoryService.save(dto, principal);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {

        Category category = categoryService.getCategoryById(id);

        model.addAttribute("category", category);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("budgetUsers", budgetUserService.getAllUsers());

        return "update_category";
    }

    @PostMapping("/categories/{id}")
    public String saveUpdatedCategory(@PathVariable Long id, @ModelAttribute("category") UpdateCategoryDto dto) {
        categoryService.updateCategory(dto);
        return "redirect:/categories";
    }
}
