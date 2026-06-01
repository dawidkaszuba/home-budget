package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawidkaszuba.homebudget.exceptions.AccountNotFoundException;
import pl.dawidkaszuba.homebudget.exceptions.SameAccountTransferException;
import pl.dawidkaszuba.homebudget.mapper.TransferMapper;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.db.Transfer;
import pl.dawidkaszuba.homebudget.model.dto.transfer.CreateTransferDto;
import pl.dawidkaszuba.homebudget.model.dto.transfer.TransferViewDto;
import pl.dawidkaszuba.homebudget.repository.AccountRepository;
import pl.dawidkaszuba.homebudget.repository.TransferRepository;
import pl.dawidkaszuba.homebudget.service.BudgetUserService;
import pl.dawidkaszuba.homebudget.service.TransferService;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final BudgetUserService budgetUserService;
    private final TransferMapper transferMapper;

    @Transactional
    @Override
    public void createTransfer(CreateTransferDto dto, Principal principal) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByPrincipal(principal);
        Home home = budgetUser.getHome();

        if (dto.getSourceAccountId().equals(dto.getTargetAccountId())) {
            throw new SameAccountTransferException("Konto źródłowe i docelowe nie mogą być takie same");
        }

        Account source = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Account target = accountRepository.findById(dto.getTargetAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!source.getHome().getId().equals(home.getId()) || !target.getHome().getId().equals(home.getId())) {
            throw new AccountNotFoundException("Account does not belong to the user's home");
        }

        Transfer transfer = new Transfer();
        transfer.setHome(home);
        transfer.setSourceAccount(source);
        transfer.setTargetAccount(target);
        transfer.setValue(dto.getValue());
        transfer.setNote(dto.getNote());

        transferRepository.save(transfer);
    }

    @Transactional
    @Override
    public void deleteTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found: " + id));
        transferRepository.delete(transfer);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransferViewDto> findAllByHome(Principal principal, Pageable pageable) {
        BudgetUser budgetUser = budgetUserService.getBudgetUserByPrincipal(principal);
        Home home = budgetUser.getHome();
        return transferRepository.findAllByHome(home, pageable)
                .map(transferMapper::toViewDto);
    }

}
