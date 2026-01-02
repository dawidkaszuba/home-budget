package pl.dawidkaszuba.homebudget.service;

import java.security.Principal;

public interface AccountService {
    Object findAllUserAccounts(Principal principal);
}
