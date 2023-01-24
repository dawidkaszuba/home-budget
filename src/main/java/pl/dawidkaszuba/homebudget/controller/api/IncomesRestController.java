package pl.dawidkaszuba.homebudget.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomesRestController {

    private final IncomeService incomeService;

    public IncomesRestController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<Income> getAllIncomesByUser(Principal principal) {
        return incomeService.getAllIncomesByUser(principal.getName());
    }
}
