package pl.dawidkaszuba.homebudget.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "INCOMES")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationTime;
    private LocalDateTime lastEditTime;
    @ManyToOne
    private BudgetUser budgetUser;
    @ManyToOne
    private Category category;
    private double value;
}
