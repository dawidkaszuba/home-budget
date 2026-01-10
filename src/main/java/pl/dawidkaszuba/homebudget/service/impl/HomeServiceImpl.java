package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.HomeService;

import java.security.Principal;

@RequiredArgsConstructor
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    private final BudgetUserService budgetUserService;


    @Transactional(readOnly = true)
    public Home getHomeByPrincipal(Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByPrincipal(principal);
        return budgetUser.getHome();
    }

    @Transactional
    @Override
    public void updateName(String name, Principal principal) {
        Home home = getHomeByPrincipal(principal);

        if (!name.equals(home.getName())) {
            home.setName(name);
            log.info("User {} changed home's name to '{}'", principal.getName(), name);
        }
    }
}
