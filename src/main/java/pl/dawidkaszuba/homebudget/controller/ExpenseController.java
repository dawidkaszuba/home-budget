package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.expenseMapper = expenseMapper;
    }

    @GetMapping("/expenses")
    public String listExpenses(Model model, Principal principal) {
        model.addAttribute("expenses", expenseService.getAllExpensesByBudgetUser(principal.getName()));
        return "expenses";
    }

    @GetMapping("/expenses/new")
    public String addNewExpense(Model model) {
        CreateExpenseDto dto = new CreateExpenseDto();
        model.addAttribute("expense", dto);
        model.addAttribute("categories", categoryService.findByCategoryType(CategoryType.EXPENSE));
        return "create_expense";
    }

    @PostMapping("/expenses")
    public String saveExpense(@ModelAttribute("expense") CreateExpenseDto dto, Principal principal) {
        expenseService.save(dto, principal);
        return "redirect:/expenses";
    }


    @GetMapping("/expenses/edit/{id}")
    public String getExpenseForUpdate(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);

        model.addAttribute("expense",
                expenseMapper.toUpdateExpenseDto(expense));

        model.addAttribute("categories",
                categoryService.findByCategoryType(CategoryType.EXPENSE));

        return "update_expense";
    }

    @PostMapping("/expenses/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute("expense") UpdateExpenseDto dto) {
        expenseService.updateExpense(dto);
        return "redirect:/expenses";
    }

    @DeleteMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteIncome(id);
        return "redirect:/expenses";
    }
}
