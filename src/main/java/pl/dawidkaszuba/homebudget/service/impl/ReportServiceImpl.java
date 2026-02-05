package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.IncomeService;
import pl.dawidkaszuba.homebudget.service.ReportService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @Override
    public List<CategoryAmountDto> getExpensesByCategory(
            LocalDate from,
            LocalDate to,
            Principal principal) {

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);


        return expenseService.getAllExpensesByHomeAndCategory(principal, fromDateTime, toDateTime);
    }

    @Override
    public List<CategoryAmountDto> getIncomesByCategory(LocalDate from, LocalDate to, Principal principal) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);
        return incomeService.getAllIncomesByHomeAndCategory(principal, fromDateTime, toDateTime);
    }
}