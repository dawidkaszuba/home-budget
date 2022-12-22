package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

@Controller
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
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
        return "create_expense";
    }

    @PostMapping("/expenses")
    public String saveExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.save(expense);
        return "redirect:/expenses";
    }
}
