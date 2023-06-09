package ru.pfr.timeTracking.repository.timeTracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.timeTracking.model.acs.entity.Accounts;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeTracking;

public interface TimeTrackingRepository extends JpaRepository<TimeTracking, String>, JpaSpecificationExecutor<TimeTracking> {
}
