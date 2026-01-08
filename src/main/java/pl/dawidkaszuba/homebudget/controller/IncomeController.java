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
import pl.dawidkaszuba.homebudget.mapper.IncomeMapper;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryService categoryService;
    private final IncomeMapper incomeMapper;
    private final AccountService accountService;
    private final DomainExceptionMapper domainExceptionMapper;


    @GetMapping("/incomes")
    public String listIncomes(
            Model model,
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Income> incomesPage =
                incomeService.getAllIncomesByUser(principal.getName(), pageable);

        model.addAttribute("incomes",
                incomesPage.getContent().stream()
                        .map(incomeMapper::toViewDto)
                        .toList());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", incomesPage.getTotalPages());

        return "incomes/incomes";
    }


    @GetMapping("/incomes/new")
    public String addNewIncome(Model model, Principal principal) {
        model.addAttribute("income", new CreateIncomeDto());
        prepareIncomeForm(model, principal);
        return "incomes/form";
    }

    @PostMapping("/incomes")
    public String saveIncome(@Valid @ModelAttribute("income") CreateIncomeDto dto,
                             BindingResult bindingResult,
                             Principal principal,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        try {
            incomeService.save(dto, principal);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        return "redirect:/incomes";
    }

    @GetMapping("/incomes/edit/{id}")
    public String getIncomeForUpdate(@PathVariable Long id, Model model, Principal principal) {
        Income income = incomeService.getIncomeById(id);
        model.addAttribute("income", incomeMapper.toUpdateIncomeDto(income));
        prepareIncomeForm(model, principal);
        return "incomes/form";
    }

    @PostMapping("/incomes/{id}")
    public String saveUpdatedIncome(@Valid @ModelAttribute("income") UpdateIncomeDto dto,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        try {
            incomeService.updateIncome(dto);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return backToExpenseForm(model, principal);
        }

        return "redirect:/incomes";
    }

    @DeleteMapping("/incomes/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return "redirect:/incomes";
    }

    private void prepareIncomeForm(Model model, Principal principal) {
        model.addAttribute(
                "categories",
                categoryService.findByCategoryType(CategoryType.INCOME)
        );
        model.addAttribute(
                "accounts",
                accountService.findAllUserAccounts(principal)
        );
    }

    private String backToExpenseForm(Model model, Principal principal) {
        prepareIncomeForm(model, principal);
        return "incomes/form";
    }

}
