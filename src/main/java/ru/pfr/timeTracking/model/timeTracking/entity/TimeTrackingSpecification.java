package ru.pfr.timeTracking.model.timeTracking.entity;

import org.springframework.data.jpa.domain.Specification;
import ru.pfr.timeTracking.model.acs.entity.*;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.UUID;

public class TimeTrackingSpecification {

    public static Specification<TimeTracking> currentDateEqual(LocalDate currentDate) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TimeTracking_.CURRENT_DATE), currentDate);
        };
    }

    public static Specification<TimeTracking> loginEqual(String login) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TimeTracking_.LOGIN), login);
    }

    public static Specification<TimeTracking> idEqual(UUID id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TimeTracking_.ID), id);
    }

    public static Specification<TimeTracking> now(String login, LocalDate currentDate) {
        return loginEqual(login).and(
                currentDateEqual(currentDate)
        );
    }

}
