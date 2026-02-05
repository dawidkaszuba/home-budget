package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.db.UserCredential;
import pl.dawidkaszuba.homebudget.model.dto.register.InviteUserDto;

import java.security.Principal;

public interface InvitationUserService {

    UserCredential inviteUserToHome(InviteUserDto dto, Principal principal);

    void resendInvitation(Long id);
}
