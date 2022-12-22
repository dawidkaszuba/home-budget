package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.CategoryType;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.time.LocalDateTime;

@Controller
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryService categoryService;

    public IncomeController(IncomeService incomeService, CategoryService categoryService) {
        this.incomeService = incomeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/incomes")
    public String listIncomes(Model model ) {
        model.addAttribute("incomes", incomeService.getAllIncomes());
        return "incomes";
    }

    @GetMapping("/incomes/new")
    public String addNewIncome(Model model) {
        Income income = new Income();
        model.addAttribute("income", income);
        model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.INCOME));
        return "create_income";
    }

    @PostMapping("/incomes")
    public String saveIncome(@ModelAttribute("income") Income income) {
        income.setCreateTime(LocalDateTime.now());
        income.setLastEditTime(LocalDateTime.now());
        incomeService.save(income);
        return "redirect:/incomes";
    }

    @GetMapping("/incomes/edit/{id}")
    public String updateIncome(@PathVariable Long id, Model model) {
        if(incomeService.getIncomeById(id).isPresent()) {
            model.addAttribute("income", incomeService.getIncomeById(id).get());
            model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.INCOME));
            return "update_income";
        }
        return "redirect:incomes"; //todo zrobić obsługę błędów
    }

    @PostMapping("/incomes/{id}")
    public String saveUpdatedIncome(@PathVariable Long id, @ModelAttribute("income") Income income) {
        if(incomeService.getIncomeById(id).isPresent()) {
            incomeService.updateIncome(income);
            return "redirect:/incomes";
        }
        //todo obsługa błędów
        return "/incomes/edit/" + id;
    }
}
