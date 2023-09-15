package ru.pfr.timeTracking.repository.acs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.acs.entity.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, Long>, JpaSpecificationExecutor<Employees> {
}
