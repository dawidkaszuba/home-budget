package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.security.Principal;

@Controller
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryService categoryService;
    private final BudgetUserService budgetUserService;

    public IncomeController(IncomeService incomeService, CategoryService categoryService, BudgetUserService budgetUserService) {
        this.incomeService = incomeService;
        this.categoryService = categoryService;
        this.budgetUserService = budgetUserService;
    }

    @GetMapping("/incomes")
    public String listIncomes(Model model, Principal principal) {
        model.addAttribute("incomes", incomeService.getAllIncomesByUser(principal.getName()));
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
    public String saveIncome(@ModelAttribute("income") Income income, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        income.setBudgetUser(budgetUser);
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

    @DeleteMapping("/incomes/delete/{id}")
    public String deleteIncome(@PathVariable Long id, @ModelAttribute("income") Income income) {
        if(incomeService.getIncomeById(id).isPresent()) {
            incomeService.deleteIncome(income);
            return "redirect:/incomes";
        }
        //todo obsługa błędów
        return "/incomes/edit/" + id;
    }
}
