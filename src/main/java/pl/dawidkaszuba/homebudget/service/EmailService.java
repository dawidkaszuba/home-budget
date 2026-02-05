package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.db.UserCredential;

public interface EmailService {
    void sendActivationEmail(UserCredential credential);
}
