package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import pl.dawidkaszuba.homebudget.model.db.Account;
import pl.dawidkaszuba.homebudget.model.dto.account.AccountViewDto;
import pl.dawidkaszuba.homebudget.model.dto.account.UpdateAccountDto;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountViewDto toAccountViewDto(Account account);

    UpdateAccountDto toUpdateAccountDto(Account account);
}
