package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dawidkaszuba.homebudget.service.HomeService;
import pl.dawidkaszuba.homebudget.service.SummaryService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SummaryService summaryService;
    private final HomeService homeService;

    @GetMapping("/")
    public String getHome(Model model, Principal principal) {
        LocalDate currentDate = LocalDate.now();
        Locale locale = new Locale("pl");
        model.addAttribute(summaryService.getSummary(principal));
        model.addAttribute("currentMonth", currentDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, locale));
        model.addAttribute("currentYear", currentDate.getYear());
        return "index";
    }
}
