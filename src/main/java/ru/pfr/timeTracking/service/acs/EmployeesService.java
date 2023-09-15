package ru.pfr.timeTracking.service.acs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.acs.entity.Employees;
import ru.pfr.timeTracking.repository.acs.AccountsRepository;
import ru.pfr.timeTracking.repository.acs.EmployeesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager="acsTransactionManager")
public class EmployeesService {

    private final EmployeesRepository repository;

    public List<Employees> findAll(Specification<Employees> specification) {
        return repository.findAll(specification);
    }
    public Optional<Employees> findOne(Specification<Employees> specification) {
        return repository.findOne(specification);
    }

}
