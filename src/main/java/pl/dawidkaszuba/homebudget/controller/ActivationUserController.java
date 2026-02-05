package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dawidkaszuba.homebudget.exceptions.TokenDoesNotExistsException;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.model.dto.register.SetPasswordDto;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;

@RequiredArgsConstructor
@Controller
public class ActivationUserController {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/activate")
    public String activateUserForm(@RequestParam("token") String token, Model model) {
        userCredentialRepository.findByActivationToken(token)
                .orElseThrow(() -> new TokenDoesNotExistsException("Invalid activation token"));

        model.addAttribute("activationToken", token);
        model.addAttribute("passwordForm", new SetPasswordDto());
        return "user/activate";
    }

    @PostMapping("/activate")
    public String activateUser(@ModelAttribute("passwordForm") @Valid SetPasswordDto dto,
                               BindingResult bindingResult,
                               @RequestParam("token") String token,
                               Model model) {

        if (!dto.getPassword().equals(dto.getConfirmedPassword())) {
            bindingResult.rejectValue(
                    "confirmedPassword",
                    "user.confirmedPassword.mismatch");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("activationToken", token);
            return "user/activate";
        }

        UserCredential credential = userCredentialRepository.findByActivationToken(token)
                .orElseThrow(() -> new TokenDoesNotExistsException("Invalid activation token"));

        credential.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        credential.setEnabled(true);
        credential.setActivationToken(null);

        userCredentialRepository.save(credential);

        return "redirect:/login?activated";
    }

}
