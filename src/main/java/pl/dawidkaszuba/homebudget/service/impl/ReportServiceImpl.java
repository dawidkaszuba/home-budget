package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportFilterDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;
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

    @Override
    public List<ReportRowDto> generateCustomReport(ReportFilterDto filter, Principal principal) {

        LocalDateTime from = filter.getFrom() != null
                ? filter.getFrom().atStartOfDay()
                : LocalDate.MIN.atStartOfDay();

        LocalDateTime to = filter.getTo() != null
                ? filter.getTo().atTime(LocalTime.MAX)
                : LocalDateTime.now();

        if (filter.getCategoryType() == CategoryType.EXPENSE) {
            return expenseService.findForReport(principal, filter.getCategoryIds(), from, to);
        }

        if (filter.getCategoryType() == CategoryType.INCOME) {
            return incomeService.findForReport(principal, filter.getCategoryIds(), from, to);
        }

        return List.of();
    }
}