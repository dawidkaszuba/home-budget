package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportFilterDto;
import pl.dawidkaszuba.homebudget.model.dto.report.ReportRowDto;
import pl.dawidkaszuba.homebudget.service.CategoryService;
import pl.dawidkaszuba.homebudget.service.ReportService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final CategoryService categoryService;

    @GetMapping
    public String reports(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Principal principal,
            Model model
    ) {
        if (from == null || to == null) {
            from = LocalDate.now().withDayOfMonth(1);
            to = LocalDate.now();
        }

        List<CategoryAmountDto> expensesByCategory =
                reportService.getExpensesByCategory(from, to, principal);

        List<CategoryAmountDto> incomesByCategory =
                reportService.getIncomesByCategory(from, to, principal);

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("expensesByCategory", expensesByCategory);
        model.addAttribute("incomesByCategory", incomesByCategory);

        return "reports/report";
    }

    @GetMapping("/custom")
    public String customReport(@ModelAttribute ReportFilterDto filter,
                               Model model,
                               Principal principal) {

        if (filter.getFrom() == null || filter.getTo() == null) {
            filter.setFrom(LocalDate.now().withDayOfMonth(1));
            filter.setTo(LocalDate.now());
        }

        model.addAttribute("filter", filter);

        if (filter.getCategoryType() != null) {

            model.addAttribute(
                    "categories",
                    categoryService.findByCategoryType(filter.getCategoryType(), principal));

            List<ReportRowDto> rows =
                    reportService.generateCustomReport(filter, principal);

            model.addAttribute("rows", rows);

            filter.setCategoryIds(new ArrayList<>());
        }

        return "reports/custom-report";
    }



}
