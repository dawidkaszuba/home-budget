package pl.dawidkaszuba.homebudget.controller;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dawidkaszuba.homebudget.service.HomeService;

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
    public String getHome(Model model) {
        LocalDate currentDate = LocalDate.now();
        long userId = 1;
        Locale locale = new Locale("pl");
        model.addAttribute(homeService.getSummary(userId));
        model.addAttribute("currentMonth", currentDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, locale));
        model.addAttribute("currentYear", currentDate.getYear());

        return "index";
    }
}
