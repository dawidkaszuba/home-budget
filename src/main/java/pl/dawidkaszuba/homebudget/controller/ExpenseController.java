package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.exceptions.AccountNotFoundException;
import pl.dawidkaszuba.homebudget.exceptions.CategoryNotBelongToHomeException;
import pl.dawidkaszuba.homebudget.exceptions.CategoryNotFoundException;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ExpenseMapper expenseMapper;
    private final AccountService accountService;

    @GetMapping("/expenses")
    public String listExpenses(Model model, Principal principal) {
        List<Expense> expenses = expenseService.getAllExpensesByBudgetUser(principal.getName());
        model.addAttribute("expenses", expenses.stream().map(expenseMapper::toViewDto).toList());
        return "expenses/expenses";
    }

    @GetMapping("/expenses/new")
    public String addNewExpense(Model model, Principal principal) {
        model.addAttribute("expense", new CreateExpenseDto());
        prepareExpenseForm(model, principal);
        return "expenses/form";
    }

    @PostMapping("/expenses")
    public String saveExpense(@Valid @ModelAttribute("expense") CreateExpenseDto dto,
                              BindingResult bindingResult,
                              Principal principal,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        try {
            expenseService.save(dto, principal);
        } catch (RuntimeException e) {
            mapExpenseException(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        return "redirect:/expenses";
    }



    @GetMapping("/expenses/edit/{id}")
    public String getExpenseForUpdate(@PathVariable Long id, Model model, Principal principal) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expenseMapper.toUpdateExpenseDto(expense));
        prepareExpenseForm(model, principal);
        return "expenses/form";
    }

    @PostMapping("/expenses/{id}")
    public String updateExpense(@PathVariable Long id,
                                @ModelAttribute("expense") UpdateExpenseDto dto,
                                BindingResult bindingResult,
                                Principal principal,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        try {
            expenseService.updateExpense(dto);
        } catch (RuntimeException e) {
            mapExpenseException(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        return "redirect:/expenses";
    }

    @DeleteMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteIncome(id);
        return "redirect:/expenses";
    }

    private String backToExpenseForm(Model model, Principal principal) {
        prepareExpenseForm(model, principal);
        return "expenses/form";
    }

    private void prepareExpenseForm(Model model, Principal principal) {
        model.addAttribute(
                "categories",
                categoryService.findByCategoryType(CategoryType.EXPENSE)
        );
        model.addAttribute(
                "accounts",
                accountService.findAllUserAccounts(principal)
        );
    }

    private void mapExpenseException(
            RuntimeException e,
            BindingResult bindingResult) {

        if (e instanceof AccountNotFoundException) {
            bindingResult.rejectValue(
                    "accountId",
                    "account.notFound",
                    e.getMessage()
            );
        } else if (e instanceof CategoryNotFoundException) {
            bindingResult.rejectValue(
                    "categoryId",
                    "category.notFound",
                    e.getMessage()
            );
        } else if (e instanceof CategoryNotBelongToHomeException) {
            bindingResult.reject(
                    "access.denied",
                    e.getMessage()
            );
        } else {
            throw e; // todo  nieznany wyjątek → ControllerAdvice
        }
    }


}
