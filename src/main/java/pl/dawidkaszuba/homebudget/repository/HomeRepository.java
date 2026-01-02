package pl.dawidkaszuba.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawidkaszuba.homebudget.model.db.Home;

public interface HomeRepository extends JpaRepository<Home, Long> {
}
