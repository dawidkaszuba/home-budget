package pl.dawidkaszuba.homebudget.model.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "expenses")
@SQLDelete(sql = "UPDATE expenses SET deleted_at = now() WHERE id = ?")
public class Expense extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "budget_user_id", nullable = false)
    private BudgetUser budgetUser;
    @ManyToOne
    private Category category;
    private BigDecimal value;

}
