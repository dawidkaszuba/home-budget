package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.Summary;

public interface HomeService {
    Summary getSummary(String userName);
}
