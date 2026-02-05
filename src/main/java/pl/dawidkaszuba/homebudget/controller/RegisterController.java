package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dawidkaszuba.homebudget.model.dto.register.RegisterForm;
import pl.dawidkaszuba.homebudget.service.UserRegistrationService;

@RequiredArgsConstructor
@Controller
public class RegisterController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("form") RegisterForm form,
                                 BindingResult bindingResult,
                                 Model model) {

        if (!form.getPassword().equals(form.getConfirmedPassword())) {
            bindingResult.rejectValue(
                    "confirmedPassword",
                    "user.confirmedPassword.mismatch");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userRegistrationService.registerUser(form);
        return "redirect:/login?registered";

    }
}
