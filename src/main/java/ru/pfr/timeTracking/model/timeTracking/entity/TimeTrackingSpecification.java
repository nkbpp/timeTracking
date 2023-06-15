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

    public static Specification<TimeTracking> orgCodeEqual(String orgCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TimeTracking_.ORG_CODE), orgCode);
    }
    public static Specification<TimeTracking> currentDateGt(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(TimeTracking_.CURRENT_DATE), date);
    }

    public static Specification<TimeTracking> currentDateLt(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(TimeTracking_.CURRENT_DATE), date);
    }

    public static Specification<TimeTracking> now(String login, LocalDate currentDate) {
        return loginEqual(login).and(
                currentDateEqual(currentDate)
        );
    }

    public static Specification<TimeTracking> getOtdelPeriod(String orgCode, LocalDate dateS, LocalDate datePo) {
        return orgCodeEqual(orgCode)
                .and(currentDateGt(dateS))
                .and(currentDateLt(datePo));
    }

}
