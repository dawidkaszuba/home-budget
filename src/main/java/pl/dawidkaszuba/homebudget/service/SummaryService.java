package pl.dawidkaszuba.homebudget.service;

import pl.dawidkaszuba.homebudget.model.dto.Summary;

import java.security.Principal;

public interface SummaryService {
    Summary getSummary(Principal principal);
}
