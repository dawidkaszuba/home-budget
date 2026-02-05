package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.exceptions.DomainExceptionMapper;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ExpenseMapper expenseMapper;
    private final AccountService accountService;
    private final DomainExceptionMapper domainExceptionMapper;

    @GetMapping("/expenses")
    public String listExpenses(Model model,
                               Principal principal,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> expensePage = expenseService.getAllExpensesByBudgetUser(principal, pageable);
        model.addAttribute("expenses", expensePage.stream().map(expenseMapper::toViewDto).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", expensePage.getTotalPages());
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
            domainExceptionMapper.map(e, bindingResult);
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
    public String updateExpense(@Valid @ModelAttribute("expense") UpdateExpenseDto dto,
                                BindingResult bindingResult,
                                Principal principal,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        try {
            expenseService.updateExpense(dto);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
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
                categoryService.findByCategoryType(CategoryType.EXPENSE, principal)
        );
        model.addAttribute(
                "accounts",
                accountService.findAllUserAccounts(principal)
        );
    }

}
