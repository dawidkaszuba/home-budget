package pl.dawidkaszuba.homebudget.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.mapper.IncomeMapper;
import pl.dawidkaszuba.homebudget.model.db.Category;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.IncomeViewDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;
import pl.dawidkaszuba.homebudget.repository.CategoryRepository;
import pl.dawidkaszuba.homebudget.repository.IncomeRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final BudgetUserService budgetUserService;
    private final CategoryRepository categoryRepository;
    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeRepository incomeRepository,
                             BudgetUserService budgetUserService,
                             CategoryRepository categoryRepository,
                             IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.budgetUserService = budgetUserService;
        this.categoryRepository = categoryRepository;
        this.incomeMapper = incomeMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<IncomeViewDto> getAllIncomesByUser(String userName) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(userName);
        return incomeRepository.findAllByBudgetUser(budgetUser)
                .stream()
                .map(incomeMapper::toDto)
                .toList();
    }


    @Transactional
    @Override
    public void save(CreateIncomeDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByUserName(principal.getName());
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Income income = incomeMapper.toEntity(dto);

        income.setBudgetUser(budgetUser);
        income.setCategory(category);
        incomeRepository.save(income);
    }

    @Transactional(readOnly = true)
    @Override
    public Income getIncomeById(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        return optionalIncome.orElseThrow();
    }

    @Transactional
    @Override
    public void updateIncome(UpdateIncomeDto dto) {

        Income income = incomeRepository
                .findById(dto.getId())
                .orElseThrow();

        if(!income.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryRepository
                    .findById(dto.getCategoryId())
                    .orElseThrow();
            income.setCategory(category);
        }

        income.setValue(income.getValue());
        income.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public Double getSumOfAllIncomesByUserAndTimeBetween(BudgetUser budgetUser, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return incomeRepository.findSumOfValueByUserAndCreateTimeBetween(budgetUser, startDateTime, endDateTime);
    }

    @Transactional
    @Override
    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow();
        incomeRepository.delete(income);
    }
}
