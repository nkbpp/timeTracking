package ru.pfr.timeTracking.service.timeTracking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;
import ru.pfr.timeTracking.repository.timeTracking.TimeTrackingRepository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(transactionManager = "timetrackingTransactionManager")
public class TimeTrackingService {

    private final TimeTrackingRepository repository;

    public void update(TimeTracking carer) {
        repository.save(carer);
    }

    public void save(TimeTracking carer) {
        repository.save(carer);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<TimeTracking> findAll(Specification<TimeTracking> specification) {
        return repository.findAll(specification);
    }
    public Optional<TimeTracking> findOne(Specification<TimeTracking> specification) {
        return repository.findOne(specification);
    }

}
