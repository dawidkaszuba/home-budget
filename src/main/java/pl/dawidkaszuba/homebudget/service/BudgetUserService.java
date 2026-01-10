package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;

import java.security.Principal;


@Service
public interface BudgetUserService {

    BudgetUser getBudgetUserByPrincipal(Principal principal);

}
