package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.mapper.AccountMapper;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.db.Expense;
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

    @GetMapping
    public String getAllAccounts(Model model, Principal principal) {

        List<Account> accounts = accountService.findAllUserAccounts(principal);

        List<AccountViewDto> accountViewDtos = accounts
                .stream()
                .map(accountMapper::toAccountViewDto)
                .toList();

        model.addAttribute("accounts", accountViewDtos);
        return "accounts";
    }

    @GetMapping("/new")
    public String addNewAccount(Model model) {
        CreateAccountDto dto = new CreateAccountDto();
        model.addAttribute("account", dto);
        return "create_account";
    }

    @PostMapping
    public String createAccount(@ModelAttribute("account") CreateAccountDto dto, Principal principal) {
        accountService.createAccount(dto, principal);
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String getAccountForUpdate(@PathVariable Long id, Model model) {
        Account account = accountService.findAccountById(id);

        model.addAttribute("account",
                accountMapper.toUpdateAccountDto(account));

        return "update_account";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") UpdateAccountDto dto,
                                Principal principal) {
        accountService.updateAccount(dto, principal);
        return "redirect:/accounts";
    }


}
