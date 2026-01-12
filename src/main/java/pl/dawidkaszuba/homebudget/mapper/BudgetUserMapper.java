package pl.dawidkaszuba.homebudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.dawidkaszuba.homebudget.model.AuthProvider;
import pl.dawidkaszuba.homebudget.model.db.BudgetUser;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.model.dto.register.UserViewDto;

@Mapper(componentModel = "spring")
public interface BudgetUserMapper {

    @Mapping(target = "email", source = "budgetUser", qualifiedByName = "extractEmail")
    @Mapping(target = "status", source = "budgetUser", qualifiedByName = "extractStatus")
    UserViewDto userViewDto(BudgetUser budgetUser);

    @Named("extractEmail")
    default String extractEmail(BudgetUser user) {
        return getLocalCredential(user).getEmail();
    }

    @Named("extractStatus")
    default UserViewDto.UserStatus extractStatus(BudgetUser user) {

        UserCredential credential = getLocalCredential(user);

        if (credential.isEnabled()) {
            return UserViewDto.UserStatus.ACTIVE;
        }

        if (credential.getActivationToken() != null) {
            return UserViewDto.UserStatus.INVITED;
        }

        return UserViewDto.UserStatus.NOT_ACTIVE;
    }


    default UserCredential getLocalCredential(BudgetUser user) {
        return user.getCredentials()
                .stream()
                .filter(c -> c.getProvider() == AuthProvider.LOCAL)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("LOCAL credential not found"));
    }
}
