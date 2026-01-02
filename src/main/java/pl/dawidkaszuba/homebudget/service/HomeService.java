package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.db.Home;

public interface HomeService {
    Home getHomeByBudgetUser(String name);
}
