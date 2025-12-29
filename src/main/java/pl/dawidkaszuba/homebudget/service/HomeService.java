package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.dto.Summary;

public interface HomeService {
    Summary getSummary(String userName);
}
