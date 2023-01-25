package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.model.UserReport;
import pl.dawidkaszuba.homebudget.service.impl.RtfReportServiceImpl;

import java.io.FileNotFoundException;
import java.security.Principal;

@Controller
public class ReportController {

    private final RtfReportServiceImpl rtfReportService;

    public ReportController(RtfReportServiceImpl rtfReportService) {
        this.rtfReportService = rtfReportService;
    }

    @PostMapping("/generate-report")
    public String showReportOptions(@ModelAttribute("userReport") UserReport report, Principal principal) {
        try {
            rtfReportService.generateReport(report.getReportType(), principal.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
