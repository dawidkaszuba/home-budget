package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.model.BudgetUser;
import pl.dawidkaszuba.homebudget.repository.IncomeRepository;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Income save(Income income) {
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
        income.setLastEditTime(LocalDateTime.now());
        income.setCreationTime(expenseFromDb.getCreationTime());
        return incomeRepository.save(income);
    }

    @Override
    public Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return incomeRepository.findSumOfValueByUserAndCreateTimeBetween(budgetUser, startDateTime, endDateTime);
    }
}
