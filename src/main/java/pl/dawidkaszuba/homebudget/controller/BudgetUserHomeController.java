package pl.dawidkaszuba.homebudget.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dawidkaszuba.homebudget.mapper.HomeMapper;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.home.HomeViewDto;
import pl.dawidkaszuba.homebudget.model.dto.home.UpdateHomeDto;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class BudgetUserHomeController {

    private final HomeService homeService;
    private final HomeMapper homeMapper;

    @GetMapping
    public String getHome(Model model, Principal principal) {
        Home home = homeService.getHomeByBudgetUser(principal.getName());
        HomeViewDto dto = homeMapper.toViewDto(home);

        model.addAttribute("home", dto);
        return "home";
    }

    @GetMapping("/edit")
    public String updateCategory(Model model, Principal principal) {

        Home home = homeService.getHomeByBudgetUser(principal.getName());
        UpdateHomeDto dto = homeMapper.toUpdateHomeDto(home);

        model.addAttribute("home", dto);

        return "update_home";
    }

    @PostMapping("/edit")
    public String updateHome(@ModelAttribute("home") UpdateHomeDto dto, Principal principal) {
        homeService.updateName(dto.getName(), principal);
        return "redirect:/home";
    }

}
