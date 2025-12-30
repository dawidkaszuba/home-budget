package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.mapper.IncomeMapper;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.security.Principal;

@Controller
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryService categoryService;
    private final IncomeMapper incomeMapper;

    public IncomeController(IncomeService incomeService, CategoryService categoryService, IncomeMapper incomeMapper) {
        this.incomeService = incomeService;
        this.categoryService = categoryService;
        this.incomeMapper = incomeMapper;
    }

    @GetMapping("/incomes")
    public String listIncomes(Model model, Principal principal) {
        model.addAttribute("incomes", incomeService.getAllIncomesByUser(principal.getName()));
        return "incomes";
    }

    @GetMapping("/incomes/new")
    public String addNewIncome(Model model) {
        CreateIncomeDto dto = new CreateIncomeDto();
        model.addAttribute("income", dto);
        model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.INCOME));
        return "create_income";
    }

    @PostMapping("/incomes")
    public String saveIncome(@ModelAttribute("income") CreateIncomeDto dto, Principal principal) {
        incomeService.save(dto, principal);
        return "redirect:/incomes";
    }

    @GetMapping("/incomes/edit/{id}")
    public String getIncomeForUpdate(@PathVariable Long id, Model model) {
        Income income = incomeService.getIncomeById(id);
        model.addAttribute("income", incomeMapper.toUpdateIncomeDto(income));
        model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.INCOME));
        return "update_income";
    }

    @PostMapping("/incomes/{id}")
    public String saveUpdatedIncome(@PathVariable Long id, @ModelAttribute("income") UpdateIncomeDto dto) {
        incomeService.updateIncome(dto);
        return "redirect:/incomes";
    }

    @DeleteMapping("/incomes/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return "redirect:/incomes";
    }
}
