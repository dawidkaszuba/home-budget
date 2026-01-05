package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.exceptions.DomainExceptionMapper;
import pl.dawidkaszuba.homebudget.mapper.CategoryMapper;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CreateCategoryDto;
import pl.dawidkaszuba.homebudget.model.dto.category.UpdateCategoryDto;
import pl.dawidkaszuba.homebudget.service.CategoryService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final DomainExceptionMapper domainExceptionMapper;

    @GetMapping("/categories")
    public String getAllCategories(Model model, Principal principal) {
        List<Category> categories = categoryService.getAllCategories(principal);
        model.addAttribute("categories", categories.stream().map(categoryMapper::mapToViewDto).toList());
        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String addNewCategory(Model model) {
        model.addAttribute("category", new CreateCategoryDto());
        model.addAttribute("categoryTypes", CategoryType.values());
        return "categories/form";
    }

    @PostMapping("/categories")
    public String saveCategory(@Valid @ModelAttribute("category") CreateCategoryDto dto,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return backToCategoryForm(model);
        }

        try {
            categoryService.save(dto, principal);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToCategoryForm(model);
        }

        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {

        Category category = categoryService.getCategoryById(id);

        model.addAttribute("category", categoryMapper.toUpdateCategoryDto(category));
        model.addAttribute("categoryTypes", CategoryType.values());

        return "categories/form";
    }

    @PostMapping("/categories/{id}")
    public String saveUpdatedCategory(@ModelAttribute("category") UpdateCategoryDto dto,
                                      BindingResult bindingResult,
                                      Model model) {

        if (bindingResult.hasErrors()) {
            return backToCategoryForm(model);
        }

        try {
            categoryService.updateCategory(dto);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToCategoryForm(model);
        }

        return "redirect:/categories";
    }

    private String backToCategoryForm(Model model) {
        model.addAttribute("categoryTypes", CategoryType.values());
        return "categories/form";
    }
}
