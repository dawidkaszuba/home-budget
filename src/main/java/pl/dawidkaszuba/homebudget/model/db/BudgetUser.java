package pl.dawidkaszuba.homebudget.model.db;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import pl.dawidkaszuba.homebudget.model.AuthProvider;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "budget_users")
@SQLDelete(sql = "UPDATE budget_users SET deleted_at = now() WHERE id = ?")
public class BudgetUser extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider authProvider = AuthProvider.LOCAL;
    private String role;
    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;


}
