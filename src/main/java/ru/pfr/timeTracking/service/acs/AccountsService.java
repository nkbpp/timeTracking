package ru.pfr.timeTracking.service.acs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.repository.acs.AccountsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager="acsTransactionManager")
public class AccountsService {

    private final AccountsRepository repository;

    public List<Accounts> findAll(Specification<Accounts> specification) {
        return repository.findAll(specification);
    }
    public Optional<Accounts> findOne(Specification<Accounts> specification) {
        return repository.findOne(specification);
    }

}
