package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dawidkaszuba.homebudget.model.db.Transfer;
import pl.dawidkaszuba.homebudget.model.dto.transfer.TransferViewDto;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(target = "sourceAccountName", source = "sourceAccount.name")
    @Mapping(target = "targetAccountName", source = "targetAccount.name")
    TransferViewDto toViewDto(Transfer transfer);

}
