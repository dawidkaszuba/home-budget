package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.dto.Summary;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.SummaryService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;


    @Override
    public Summary getSummary(Principal principal) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 1, 1, 0, 0);
        LocalDateTime endDateTimeAnnual = LocalDateTime.of(currentDateTime.getYear(), 12, 31, 23, 59);
        LocalDate now= LocalDate.now();
        int firstDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(1).getDayOfMonth();
        int lastDayOfCurrentMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().getMonth().length(now.isLeapYear())).getDayOfMonth();
        LocalDateTime startDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), firstDayOfCurrentMonth, 0, 0);
        LocalDateTime endDateTimeMonthly = LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), lastDayOfCurrentMonth, 0, 0);

        BigDecimal allExpensesDb = expenseService.getSumOfValueByHome(principal);
        BigDecimal annualExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(principal, startDateTimeAnnual, endDateTimeAnnual);
        BigDecimal monthlyExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(principal, startDateTimeMonthly, endDateTimeMonthly);
        BigDecimal allIncomesDb = incomeService.getSumOfValueByHome(principal);
        BigDecimal annualIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(startDateTimeAnnual, endDateTimeAnnual, principal);
        BigDecimal monthlyIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(startDateTimeMonthly, endDateTimeMonthly, principal);

        Summary summary = new Summary();

        BigDecimal annualExpenses = annualExpensesDb != null ? annualExpensesDb : BigDecimal.valueOf(0);
        BigDecimal monthlyExpenses = monthlyExpensesDb != null ? monthlyExpensesDb : BigDecimal.valueOf(0);
        BigDecimal annualIncomes = annualIncomesDb != null ? annualIncomesDb : BigDecimal.valueOf(0);
        BigDecimal monthlyIncomes = monthlyIncomesDb != null ? monthlyIncomesDb : BigDecimal.valueOf(0);
        BigDecimal allIncomes = allIncomesDb != null ? allIncomesDb : BigDecimal.valueOf(0);
        BigDecimal allExpenses = allExpensesDb != null ? allExpensesDb : BigDecimal.valueOf(0);

        summary.setBalance(allIncomes.subtract(allExpenses));
        summary.setAnnualBalance(annualIncomes.subtract(annualExpenses));
        summary.setMonthlyBalance(monthlyIncomes.subtract(monthlyExpenses));
        summary.setMonthlyIncomes(monthlyIncomes);
        summary.setMonthlyExpenses(monthlyExpenses);
        return summary;
    }
}
