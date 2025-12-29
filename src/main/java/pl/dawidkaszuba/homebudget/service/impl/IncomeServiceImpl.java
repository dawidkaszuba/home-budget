package pl.dawidkaszuba.homebudget.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.repository.IncomeRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final BudgetUserService budgetUserService;
    private final EntityManager entityManager;

    public IncomeServiceImpl(IncomeRepository incomeRepository, BudgetUserService budgetUserService, EntityManager entityManager) {
        this.incomeRepository = incomeRepository;
        this.budgetUserService = budgetUserService;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public List<Income> getAllIncomesByUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        return incomeRepository.findAllByBudgetUser(budgetUser);
    }

    @Override
    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Income save(Income income) {
        income.setCreatedAt(LocalDateTime.now());
        income.setUpdatedAt(LocalDateTime.now());
        return incomeRepository.save(income);
    }

    @Override
    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Income updateIncome(Income income) {
        Income expenseFromDb = getIncomeById(income.getId()).get();
        expenseFromDb.setCategory(income.getCategory());
        expenseFromDb.setValue(income.getValue());
        expenseFromDb.setUpdatedAt(LocalDateTime.now());
        return incomeRepository.save(expenseFromDb);
    }

    @Transactional
    @Override
    public Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        return incomeRepository.findSumOfValueByUserAndCreateTimeBetween(budgetUser, startDateTime, endDateTime);
    }

    @Override
    public void deleteIncome(Income income) {
        incomeRepository.delete(income);
    }
}
