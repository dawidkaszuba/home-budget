package pl.dawidkaszuba.homebudget.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRestController {

    private final ExpenseService expenseService;

    public ExpensesRestController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping
    public List<Expense> getAllExpensesByUser(Principal principal) {
        return expenseService.getAllExpensesByBudgetUser(principal.getName());
    }
}
