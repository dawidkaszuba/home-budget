package pl.dawidkaszuba.homebudget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidkaszuba.homebudget.exceptions.DomainExceptionMapper;
import pl.dawidkaszuba.homebudget.model.dto.transfer.CreateTransferDto;
import pl.dawidkaszuba.homebudget.model.dto.transfer.TransferViewDto;
import pl.dawidkaszuba.homebudget.service.AccountService;
import pl.dawidkaszuba.homebudget.service.TransferService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final AccountService accountService;
    private final DomainExceptionMapper domainExceptionMapper;

    @GetMapping("/transfers")
    public String listTransfers(Model model,
                                Principal principal,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransferViewDto> transferPage = transferService.findAllByHome(principal, pageable);
        model.addAttribute("transfers", transferPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transferPage.getTotalPages());
        return "transfers/list";
    }

    @GetMapping("/transfers/new")
    public String newTransferForm(Model model, Principal principal) {
        model.addAttribute("transfer", new CreateTransferDto());
        model.addAttribute("accounts", accountService.findAllUserAccounts(principal));
        return "transfers/form";
    }

    @PostMapping("/transfers")
    public String createTransfer(@Valid @ModelAttribute("transfer") CreateTransferDto dto,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAllUserAccounts(principal));
            return "transfers/form";
        }

        try {
            transferService.createTransfer(dto, principal);
        } catch (RuntimeException e) {
            domainExceptionMapper.map(e, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", accountService.findAllUserAccounts(principal));
            return "transfers/form";
        }

        return "redirect:/transfers";
    }

    @DeleteMapping("/transfers/delete/{id}")
    public String deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return "redirect:/transfers";
    }

}
