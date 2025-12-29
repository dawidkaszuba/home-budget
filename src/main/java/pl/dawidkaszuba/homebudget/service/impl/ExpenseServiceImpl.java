package pl.dawidkaszuba.homebudget.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetUserService budgetUserService;
    private final EntityManager entityManager;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, BudgetUserService budgetUserService, EntityManager entityManager) {
        this.expenseRepository = expenseRepository;
        this.budgetUserService = budgetUserService;
        this.entityManager = entityManager;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Transactional
    @Override
    public List<Expense> getAllExpensesByBudgetUser(String userName) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return expenseRepository.findAllByBudgetUser(budgetUser);
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense expenseFromDb = getExpenseById(expense.getId()).get();
        expenseFromDb.setCategory(expense.getCategory());
        expenseFromDb.setValue(expense.getValue());
        expenseFromDb.setUpdatedAt(LocalDateTime.now());
        expenseFromDb.setCreatedAt(expenseFromDb.getCreatedAt());
        return expenseRepository.save(expenseFromDb);
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Double getSumOfAllExpensesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return expenseRepository.getSumOfValueByUserAndTimeBetween(budgetUser, startDateTime, endDateTime);
    }

    @Override
    public void deleteIncome(Expense expense) {
        expenseRepository.delete(expense);
    }
}
