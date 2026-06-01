package pl.dawidkaszuba.homebudget.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.dawidkaszuba.homebudget.model.dto.transfer.CreateTransferDto;
import pl.dawidkaszuba.homebudget.model.dto.transfer.TransferViewDto;

import java.security.Principal;

public interface TransferService {

    void createTransfer(CreateTransferDto dto, Principal principal);

    void deleteTransfer(Long id);

    Page<TransferViewDto> findAllByHome(Principal principal, Pageable pageable);

}
