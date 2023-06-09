package ru.pfr.timeTracking.service.timeTracking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParam;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;
import ru.pfr.timeTracking.repository.timeTracking.TimeParamRepository;
import ru.pfr.timeTracking.repository.timeTracking.TimeTrackingRepository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(transactionManager = "timetrackingTransactionManager")
public class TimeParamService {

    private final TimeParamRepository repository;

    public void update(TimeParam carer) {
        repository.save(carer);
    }

    public void save(TimeParam carer) {
        repository.save(carer);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<TimeParam> findAll(Specification<TimeParam> specification) {
        return repository.findAll(specification);
    }
    public Optional<TimeParam> findOne(Specification<TimeParam> specification) {
        return repository.findOne(specification);
    }

}
