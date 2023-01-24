package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
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

    public IncomeServiceImpl(IncomeRepository incomeRepository, BudgetUserService budgetUserService) {
        this.incomeRepository = incomeRepository;
        this.budgetUserService = budgetUserService;
    }

    @Override
    public List<Income> getAllIncomesByUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return incomeRepository.findAllByBudgetUser(budgetUser);
    }

    @Override
    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Income save(Income income) {
        income.setCreationTime(LocalDateTime.now());
        income.setLastEditTime(LocalDateTime.now());
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
        expenseFromDb.setLastEditTime(LocalDateTime.now());
        return incomeRepository.save(expenseFromDb);
    }

    @Override
    public Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return incomeRepository.findSumOfValueByUserAndCreateTimeBetween(budgetUser, startDateTime, endDateTime);
    }
}
