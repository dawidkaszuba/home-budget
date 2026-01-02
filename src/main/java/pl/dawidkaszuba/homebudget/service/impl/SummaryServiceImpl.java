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

        Double allExpensesDb = expenseService.getSumOfValueByHome(principal);
        Double annualExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(principal, startDateTimeAnnual, endDateTimeAnnual);
        Double monthlyExpensesDb = expenseService.getSumOfAllExpensesByUserAndTimeBetween(principal, startDateTimeMonthly, endDateTimeMonthly);
        Double allIncomesDb = incomeService.getSumOfValueByHome(principal);
        Double annualIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(startDateTimeAnnual, endDateTimeAnnual, principal);
        Double monthlyIncomesDb = incomeService.getSumOfAllIncomesByUserAndTimeBetween(startDateTimeMonthly, endDateTimeMonthly, principal);

        Summary summary = new Summary();

        Double annualExpenses = annualExpensesDb != null ? annualExpensesDb : Double.valueOf(0);
        Double monthlyExpenses = monthlyExpensesDb != null ? monthlyExpensesDb : Double.valueOf(0);
        Double annualIncomes = annualIncomesDb != null ? annualIncomesDb : Double.valueOf(0);
        Double monthlyIncomes = monthlyIncomesDb != null ? monthlyIncomesDb : Double.valueOf(0);
        Double allIncomes = allIncomesDb != null ? allIncomesDb : Double.valueOf(0);
        Double allExpenses = allExpensesDb != null ? allExpensesDb : Double.valueOf(0);

        summary.setBalance(BigDecimal.valueOf(allIncomes - allExpenses));
        summary.setAnnualBalance(BigDecimal.valueOf(annualIncomes - annualExpenses));
        summary.setMonthlyBalance(BigDecimal.valueOf(monthlyIncomes - monthlyExpenses));
        summary.setMonthlyIncomes(BigDecimal.valueOf(monthlyIncomes));
        summary.setMonthlyExpenses(BigDecimal.valueOf(monthlyExpenses));
        return summary;
    }
}
