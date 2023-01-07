package pl.dawidkaszuba.homebudget.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.model.Summary;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.HomeService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public HomeServiceImpl(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @Override
    public Summary getSummary(long userId) {
        BudgetUser budgetUser = new BudgetUser();
        budgetUser.setId(userId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 1, 1, 0, 0);
        LocalDateTime endDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 12, 31, 23, 59);
        LocalDate now= LocalDate.now();
        int firstDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(1).getDayOfMonth();
        int lastDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().getMonth().length(now.isLeapYear())).getDayOfMonth();
        LocalDateTime startDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), firstDayOfCurrentMonth, 0, 0);
        LocalDateTime endDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), lastDayOfCurrentMonth, 0, 0);

        Double annualExpenses = expenseService.getSumOfAllExpensesByUserAndTimeBetween(budgetUser, startDateTimeAnnual, endDateTimeAnnual);
        Double monthlyExpenses = expenseService.getSumOfAllExpensesByUserAndTimeBetween(budgetUser, startDateTimeMonthly, endDateTimeMonthly);
        Double annualIncomes = incomeService.getSumOfAllIncomesByUserAndTimeBetween(budgetUser, startDateTimeAnnual, endDateTimeAnnual);
        Double monthlyIncomes = incomeService.getSumOfAllIncomesByUserAndTimeBetween(budgetUser, startDateTimeMonthly, endDateTimeMonthly);

        Summary summary = new Summary();
        summary.setAnnualBalance(annualIncomes - annualExpenses);
        summary.setMonthlyBalance(monthlyIncomes - monthlyExpenses);
        summary.setMonthlyIncomes(monthlyIncomes);
        summary.setMonthlyExpenses(monthlyExpenses);
        return summary;
    }
}
