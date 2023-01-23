package pl.dawidkaszuba.homebudget.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.SecurityUser;
import pl.dawidkaszuba.homebudget.repository.BudgetUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final BudgetUserRepository budgetUserRepository;

    public JpaUserDetailsService(BudgetUserRepository budgetUserRepository) {
        this.budgetUserRepository = budgetUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return budgetUserRepository
            .findByUserName(username)
            .map(SecurityUser::new)
            .orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
    }

}
