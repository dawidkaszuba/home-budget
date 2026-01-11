package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dawidkaszuba.homebudget.mapper.BudgetUserMapper;
import pl.dawidkaszuba.homebudget.model.dto.register.UserViewDto;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final BudgetUserService budgetUserService;
    private final BudgetUserMapper budgetUserMapper;

    @GetMapping
    public String listUsers(Model model, Principal principal) {

        List<UserViewDto> users = budgetUserService
                .getUsersForAdminHome(principal)
                .stream()
                .map(budgetUserMapper::userViewDto)
                .toList();

        model.addAttribute("users", users);
        return "user/users";
    }
}
