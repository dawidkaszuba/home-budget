package pl.dawidkaszuba.homebudget.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

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
    private String userName;
    private String password;
    private String roles;

}
