package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import pl.dawidkaszuba.homebudget.model.db.Home;
import pl.dawidkaszuba.homebudget.model.dto.home.HomeViewDto;
import pl.dawidkaszuba.homebudget.model.dto.home.UpdateHomeDto;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeViewDto toViewDto(Home home);

    UpdateHomeDto toUpdateHomeDto(Home home);
}
