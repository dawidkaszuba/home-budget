package pl.dawidkaszuba.homebudget.service.impl;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.mapper.ExpenseMapper;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.Expense;
import pl.dawidkaszuba.homebudget.model.dto.expense.CreateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.UpdateExpenseDto;
import pl.dawidkaszuba.homebudget.model.dto.expense.ExpenseViewDto;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.repository.ExpenseRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.ExpenseService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetUserService budgetUserService;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, BudgetUserService budgetUserService, CategoryRepository categoryRepository, EntityManager entityManager, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.budgetUserService = budgetUserService;
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
        this.expenseMapper = expenseMapper;
    }


    @Transactional(readOnly = true)
    @Override
    public List<ExpenseViewDto> getAllExpensesByBudgetUser(String userName) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return expenseRepository.findAllByBudgetUser(budgetUser)
                .stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void save(CreateExpenseDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Expense expense = expenseMapper.toEntity(dto);
        expense.setBudgetUser(budgetUser);
        expense.setCategory(category);

        expenseRepository.save(expense);
    }

    @Transactional
    @Override
    public void updateExpense(UpdateExpenseDto dto) {

        Expense expense = expenseRepository
                .findById(dto.getId())
                .orElseThrow();

        if (!expense.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryRepository
                    .findById(dto.getCategoryId())
                    .orElseThrow();
            expense.setCategory(category);
        }
        expense.setValue(dto.getValue());
        expense.setUpdatedAt(LocalDateTime.now());
        //todo  Brak save() – Hibernate zrobi dirty checking - wrzucić do lesson learned
    }

    @Transactional(readOnly = true)
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);
        return optional.orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Double getSumOfAllExpensesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        return expenseRepository.getSumOfValueByUserAndTimeBetween(budgetUser, startDateTime, endDateTime);
    }

    @Transactional
    @Override
    public void deleteIncome(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expenseRepository.delete(expense);
    }
}
