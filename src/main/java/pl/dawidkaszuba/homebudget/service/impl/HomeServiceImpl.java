package pl.dawidkaszuba.homebudget.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.SecurityUser;
import pl.dawidkaszuba.homebudget.model.Summary;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.HomeService;
import pl.dawidkaszuba.homebudget.service.IncomeService;
import pl.dawidkaszuba.homebudget.service.JpaUserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final BudgetUserService budgetUserService;

    public HomeServiceImpl(ExpenseService expenseService, IncomeService incomeService, BudgetUserService budgetUserService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.budgetUserService = budgetUserService;
    }

    @Override
    public Summary getSummary(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 1, 1, 0, 0);
        LocalDateTime endDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 12, 31, 23, 59);
        LocalDate now= LocalDate.now();
        int firstDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(1).getDayOfMonth();
        int lastDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().getMonth().length(now.isLeapYear())).getDayOfMonth();
        LocalDateTime startDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), firstDayOfCurrentMonth, 0, 0);
        LocalDateTime endDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), lastDayOfCurrentMonth, 0, 0);

        Double annualExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(budgetUser, startDateTimeAnnual, endDateTimeAnnual);
        Double monthlyExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(budgetUser, startDateTimeMonthly, endDateTimeMonthly);
        Double annualIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(budgetUser, startDateTimeAnnual, endDateTimeAnnual);
        Double monthlyIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(budgetUser, startDateTimeMonthly, endDateTimeMonthly);

        Summary summary = new Summary();

        Double annualExpenses = annualExpensesDb != null ? annualExpensesDb : Double.valueOf(0);
        Double monthlyExpenses = monthlyExpensesDb != null ? monthlyExpensesDb : Double.valueOf(0);
        Double annualIncomes = annualIncomesDb != null ? annualIncomesDb : Double.valueOf(0);
        Double monthlyIncomes = monthlyIncomesDb != null ? monthlyIncomesDb : Double.valueOf(0);

        summary.setAnnualBalance(annualIncomes - annualExpenses);
        summary.setMonthlyBalance(monthlyIncomes - monthlyExpenses);
        summary.setMonthlyIncomes(monthlyIncomes);
        summary.setMonthlyExpenses(monthlyExpenses);
        return summary;
    }
}
