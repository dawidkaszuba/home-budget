package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportFilterDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<CategoryAmountDto> getExpensesByCategory(LocalDate from, LocalDate to, Principal principal);

    List<CategoryAmountDto> getIncomesByCategory(LocalDate from, LocalDate to, Principal principal);

    List<ReportRowDto> generateCustomReport(ReportFilterDto filter, Principal principal);
}
