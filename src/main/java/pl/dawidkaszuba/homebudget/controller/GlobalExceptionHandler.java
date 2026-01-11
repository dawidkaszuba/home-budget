package pl.dawidkaszuba.homebudget.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import pl.dawidkaszuba.homebudget.exceptions.TokenDoesNotExistsException;
import pl.dawidkaszuba.homebudget.exceptions.UserAlreadyExistsException;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;


    @ExceptionHandler(TokenDoesNotExistsException.class)
    public String handleInvalidToken(
            TokenDoesNotExistsException ex,
            Model model,
            Locale locale) {

        return buildErrorPage(
                model,
                HttpStatus.GONE,
                ex.getMessage(),
                locale
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handle404(
            Model model,
            Locale locale
    ) {
        return buildErrorPage(
                model,
                HttpStatus.NOT_FOUND,
                "error.not.found",
                locale
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model, Locale locale) {
        log.warn("Access denied: {}", ex.getMessage());
        return buildErrorPage(
                model,
                HttpStatus.FORBIDDEN,
                "error.access.denied",
                locale);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model, Locale locale) {
        log.warn("User already exists: {}", ex.getMessage());
        return buildErrorPage(
                model,
                HttpStatus.BAD_REQUEST,
                "error.user.exists",
                locale);
    }


    private String buildErrorPage(
            Model model,
            HttpStatus status,
            String messageKey,
            Locale locale) {

        model.addAttribute("status", status.value());
        model.addAttribute("title", messageSource.getMessage(
                "error.title." + status.value(), null, locale));
        model.addAttribute("message", messageSource.getMessage(
                messageKey, null, locale));

        return "error/error";
    }
}
