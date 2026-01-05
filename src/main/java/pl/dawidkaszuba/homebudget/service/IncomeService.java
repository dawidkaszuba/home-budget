package pl.dawidkaszuba.homebudget.service;

import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.IncomeViewDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface IncomeService {

    List<IncomeViewDto> getAllIncomesByUser(String userName);

    void save(CreateIncomeDto dto, Principal principal);

    Income getIncomeById(Long id);

    void updateIncome(UpdateIncomeDto dto);

    BigDecimal getSumOfAllIncomesByUserAndTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Principal principal);

    void deleteIncome(Long id);

    BigDecimal getSumOfValueByHome(Principal principal);
}
