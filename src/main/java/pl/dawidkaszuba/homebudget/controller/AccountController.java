package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.exceptions.DomainExceptionMapper;
import pl.dawidkaszuba.homebudget.mapper.AccountMapper;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.dto.account.AccountViewDto;
import pl.dawidkaszuba.homebudget.model.dto.account.CreateAccountDto;
import pl.dawidkaszuba.homebudget.model.dto.account.UpdateAccountDto;
import pl.dawidkaszuba.homebudget.service.AccountService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final DomainExceptionMapper domainExceptionMapper;

    @GetMapping
    public String getAllAccounts(Model model, Principal principal) {

        List<Account> accounts = accountService.findAllUserAccounts(principal);

        List<AccountViewDto> accountViewDtos = accounts
                .stream()
                .map(accountMapper::toAccountViewDto)
                .toList();

        model.addAttribute("accounts", accountViewDtos);
        return "accounts/accounts";
    }

    @GetMapping("/new")
    public String addNewAccount(Model model) {
        CreateAccountDto dto = new CreateAccountDto();
        model.addAttribute("account", dto);
        return "accounts/form";
    }

    @PostMapping
    public String createAccount(@ModelAttribute("account") CreateAccountDto dto,
                                BindingResult bindingResult,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            return "accounts/form";
        }

        try {
            accountService.save(dto, principal);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "accounts/form";
        }

        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String getAccountForUpdate(@PathVariable Long id, Model model) {
        Account account = accountService.findAccountById(id);

        model.addAttribute("account",
                accountMapper.toUpdateAccountDto(account));

        return "accounts/form";
    }

    @PostMapping("/{id}")
    public String updateAccount(@ModelAttribute("account") UpdateAccountDto dto,
                                BindingResult bindingResult,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            return "accounts/form";
        }

        try {
            accountService.updateAccount(dto, principal);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "accounts/form";
        }

        return "redirect:/accounts";
    }

}
