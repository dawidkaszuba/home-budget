package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository
        extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByProviderAndEmail(AuthProvider provider, String email);

    Optional<UserCredential> findByProviderAndProviderUserId(AuthProvider provider,String providerUserId);
}