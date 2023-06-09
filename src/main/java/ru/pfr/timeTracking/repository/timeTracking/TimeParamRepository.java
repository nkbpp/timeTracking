package ru.pfr.timeTracking.repository.timeTracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParam;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;

public interface TimeParamRepository extends JpaRepository<TimeParam, String>, JpaSpecificationExecutor<TimeParam> {
}
