package ru.pfr.timeTracking.repository.acs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.timeTracking.model.acs.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
}
