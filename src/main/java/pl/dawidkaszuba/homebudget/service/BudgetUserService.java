package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;


@Service
public interface BudgetUserService {

    BudgetUser getBudgetUserByUserName(String userName);

}
