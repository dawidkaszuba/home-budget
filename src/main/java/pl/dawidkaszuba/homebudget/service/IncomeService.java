package pl.dawidkaszuba.homebudget.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.Income;
import pl.dawidkaszuba.homebudget.model.dto.category.CategoryAmountDto;
import pl.dawidkaszuba.homebudget.model.dto.income.CreateIncomeDto;
import pl.dawidkaszuba.homebudget.model.dto.income.UpdateIncomeDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface IncomeService {

    Page<Income> getAllIncomesByUser(Principal principal, Pageable pageable);

    void save(CreateIncomeDto dto, Principal principal);

    Income getIncomeById(Long id);

    void updateIncome(UpdateIncomeDto dto);

    BigDecimal getSumOfAllIncomesByUserAndTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Principal principal);

    void deleteIncome(Long id);

    BigDecimal getSumOfValueByHome(Principal principal);

    List<CategoryAmountDto> getAllIncomesByHomeAndCategory(Principal principal, LocalDateTime from, LocalDateTime to);
}
