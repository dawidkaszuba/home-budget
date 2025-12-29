package pl.dawidkaszuba.homebudget.model.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted_at = now() WHERE id = ?")
public class Category extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    @ManyToOne
    @JoinColumn(name = "budget_user_id", nullable = false)
    private BudgetUser budgetUser;
}

