package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.SummaryService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SummaryService summaryService;
    private final AccountService accountService;
    private final BudgetUserService budgetUserService;

    @GetMapping("/")
    public String getHome(Model model, Principal principal) {
        LocalDate currentDate = LocalDate.now();
        Locale locale = Locale.forLanguageTag("pl-PL");
        model.addAttribute(summaryService.getSummary(principal));
        model.addAttribute("currentMonth", currentDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, locale));
        model.addAttribute("currentYear", currentDate.getYear());
        model.addAttribute("accounts", accountService.findAllUserAccountsWithState(principal));

        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        model.addAttribute("userName", budgetUser.getFirstName());
        return "index";
    }
}
