package pl.dawidkaszuba.homebudget.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.dawidkaszuba.homebudget.exceptions.BudgetUserNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BudgetUserNotFoundException.class)
    public String handleBudgetUserNotFound(
            BudgetUserNotFoundException ex,
            Model model) {
        log.error("BudgetUser not found: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    //todo

//    @ExceptionHandler(AccessDeniedException.class)
//    public String handleAccessDenied(
//            AccessDeniedException ex,
//            Model model
//    ) {
//        log.warn("Access denied", ex);
//        model.addAttribute("errorMessage", "Brak dostępu");
//        return "error/403";
//    }
////
//    @ExceptionHandler(Exception.class)
//    public String handleUnexpected(
//            Exception ex,
//            Model model
//    ) {
//        log.error("Unexpected exception", ex);
//        model.addAttribute("errorMessage", "Wystąpił błąd serwera");
//        return "error/500";
//    }
}
