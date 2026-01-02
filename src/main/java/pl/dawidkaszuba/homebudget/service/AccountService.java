package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.dto.account.AccountViewStateDto;

import java.security.Principal;
import java.util.List;

public interface AccountService {

    List<Account> findAllUserAccounts(Principal principal);

    List<AccountViewStateDto> findAllUserAccountsWithState(Principal principal);

}
