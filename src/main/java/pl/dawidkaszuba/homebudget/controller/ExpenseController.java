package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.CategoryType;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final BudgetUserService budgetUserService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService, BudgetUserService budgetUserService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.budgetUserService = budgetUserService;
    }

    @GetMapping("/expenses")
    public String listExpenses(Model model ) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses";
    }

    @GetMapping("/expenses/new")
    public String addNewExpense(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.EXPENSE));
        return "create_expense";
    }

    @PostMapping("/expenses")
    public String saveExpense(@ModelAttribute("expense") Expense expense, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        expense.setCreationTime(LocalDateTime.now());
        expense.setLastEditTime(LocalDateTime.now());
        expense.setBudgetUser(budgetUser);
        expenseService.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses/edit/{id}")
    public String updateStudent(@PathVariable Long id, Model model) {
        if(expenseService.getExpenseById(id).isPresent()) {
            model.addAttribute("expense", expenseService.getExpenseById(id).get());
            model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.EXPENSE));
            return "update_expense";
        }
        return "redirect:expenses"; //todo zrobić obsługę błędów
    }

    @PostMapping("/expenses/{id}")
    public String saveUpdatedExpense(@PathVariable Long id, @ModelAttribute("expense") Expense expense) {
        if(expenseService.getExpenseById(id).isPresent()) {
            expenseService.updateExpense(expense);
            return "redirect:/expenses";
        }
        //todo obsługa błędów
        return "/expenses/edit/" + id;
    }
}
