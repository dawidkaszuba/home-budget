package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.db.BudgetUser;

import java.security.Principal;
import java.util.List;


public interface BudgetUserService {

    BudgetUser getBudgetUserByPrincipal(Principal principal);

    List<BudgetUser> getUsersForAdminHome(Principal principal);

}
