package ru.pfr.timeTracking.model.timeTracking.entity;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class TimeParamSpecification {

    public static Specification<TimeParam> codeEqual(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TimeParam_.ORG_CODE), code);
    }


}
