package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.repository.UserCredentialRepository;


import java.util.List;

@RequiredArgsConstructor
@Service
public class BudgetUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserCredential credential = credentialRepository
                .findByProviderAndEmail(AuthProvider.LOCAL, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        BudgetUser user = credential.getUser();

        return new org.springframework.security.core.userdetails.User(
                credential.getEmail(),
                credential.getPasswordHash(),
                credential.isEnabled(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
