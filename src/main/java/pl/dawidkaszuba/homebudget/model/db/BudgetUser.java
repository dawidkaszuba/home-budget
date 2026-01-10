package pl.dawidkaszuba.homebudget.model.db;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.HashSet;
import java.util.Set;

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
    private String role;
    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCredential> credentials = new HashSet<>();


    public void addCredential(UserCredential credential) {
        credentials.add(credential);
        credential.setUser(this);
    }

}
