package pl.dawidkaszuba.homebudget.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.dawidkaszuba.homebudget.model.GoldPrice;
import pl.dawidkaszuba.homebudget.service.GoldPriceService;

@ControllerAdvice
public class GoldPriceControllerAdvice {

    private final GoldPriceService goldPriceService;

    public GoldPriceControllerAdvice(GoldPriceService goldPriceService) {
        this.goldPriceService = goldPriceService;
    }

    @ModelAttribute("goldPrice")
    public GoldPrice populateUser() {
        return goldPriceService.getPrice().get(0);
    }
}