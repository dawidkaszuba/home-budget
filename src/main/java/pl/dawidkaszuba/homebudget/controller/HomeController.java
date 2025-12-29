package pl.dawidkaszuba.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dawidkaszuba.homebudget.model.ReportType;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String getHome(Model model, Principal principal) {
        LocalDate currentDate = LocalDate.now();
        Locale locale = new Locale("pl");
        model.addAttribute(homeService.getSummary(principal.getName()));
        model.addAttribute("currentMonth", currentDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, locale));
        model.addAttribute("currentYear", currentDate.getYear());
        model.addAttribute("reportTypes", ReportType.values());
        return "index";
    }
}
