package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
