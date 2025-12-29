package pl.dawidkaszuba.homebudget.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "incomes")
@SQLDelete(sql = "UPDATE incomes SET deleted_at = now() WHERE id = ?")
public class Income extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "budget_user_id", nullable = false)
    private BudgetUser budgetUser;
    @ManyToOne
    private Category category;
    private BigDecimal value;
}
