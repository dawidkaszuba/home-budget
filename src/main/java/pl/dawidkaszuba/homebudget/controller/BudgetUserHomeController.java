package pl.dawidkaszuba.homebudget.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dawidkaszuba.homebudget.exceptions.DomainExceptionMapper;
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
    private final DomainExceptionMapper domainExceptionMapper;

    @GetMapping
    public String getHome(Model model, Principal principal) {
        Home home = homeService.getHomeByBudgetUser(principal.getName());
        HomeViewDto dto = homeMapper.toViewDto(home);

        model.addAttribute("home", dto);
        return "home/home";
    }

    @GetMapping("/edit")
    public String getHomeForEdit(Model model, Principal principal) {

        Home home = homeService.getHomeByBudgetUser(principal.getName());
        UpdateHomeDto dto = homeMapper.toUpdateHomeDto(home);

        model.addAttribute("home", dto);

        return "home/form";
    }

    @PostMapping("/{id}")
    public String saveUpdatedHome(@ModelAttribute("home") UpdateHomeDto dto,
                                  BindingResult bindingResult,
                                  Principal principal) {

        if (bindingResult.hasErrors()) {
            return "home/form";
        }
        homeService.updateName(dto.getName(), principal);

        return "redirect:/home";
    }

}
