package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.model.dto.register.InviteUserDto;
import pl.dawidkaszuba.homebudget.service.InvitationUserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInvitationController {

    private final InvitationUserService invitationUserService;

    @GetMapping("/invite")
    public String showInviteForm(Model model) {
        model.addAttribute("inviteUserDto", new InviteUserDto());
        return "user/invite";
    }

    @PostMapping("/invite")
    public String sendInvite(@ModelAttribute @Valid InviteUserDto dto,
                             BindingResult result,
                             Principal principal,
                             Model model) {

        if (result.hasErrors()) {
            return "user/invite";
        }

        invitationUserService.inviteUserToHome(dto, principal);

        return "redirect:/users/invite?success";
    }

    @PostMapping("/resend/{id}")
    public String resendInvitation(@PathVariable Long id) {
        invitationUserService.resendInvitation(id);
        return "redirect:/users";
    }


}
