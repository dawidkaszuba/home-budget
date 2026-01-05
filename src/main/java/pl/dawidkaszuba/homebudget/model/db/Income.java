package pl.dawidkaszuba.homebudget.model.db;

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
    private Category category;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    private String note;

}
