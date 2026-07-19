package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.DateValueDto;
import pl.dawidkaszuba.homebudget.model.dto.report.MonthlyNetDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportFilterDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.IncomeService;
import pl.dawidkaszuba.homebudget.service.ReportService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<MonthlyNetDto> getMonthlyNet(LocalDate from, LocalDate to, Principal principal) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        Map<YearMonth, BigDecimal> expensesByMonth = groupByMonth(
                expenseService.findValuesByHomeAndTimeBetween(principal, fromDateTime, toDateTime));
        Map<YearMonth, BigDecimal> incomesByMonth = groupByMonth(
                incomeService.findValuesByHomeAndTimeBetween(principal, fromDateTime, toDateTime));

        List<MonthlyNetDto> result = new ArrayList<>();
        for (YearMonth month = YearMonth.from(from); !month.isAfter(YearMonth.from(to)); month = month.plusMonths(1)) {
            BigDecimal income = incomesByMonth.getOrDefault(month, BigDecimal.ZERO);
            BigDecimal expense = expensesByMonth.getOrDefault(month, BigDecimal.ZERO);
            result.add(new MonthlyNetDto(month, income, expense, income.subtract(expense)));
        }

        return result;
    }

    private Map<YearMonth, BigDecimal> groupByMonth(List<DateValueDto> values) {
        return values.stream()
                .collect(Collectors.groupingBy(
                        dv -> YearMonth.from(dv.getCreatedAt()),
                        Collectors.reducing(BigDecimal.ZERO, DateValueDto::getValue, BigDecimal::add)));
    }
}