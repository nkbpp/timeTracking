package ru.pfr.timeTracking.model.acs.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class AccountSpecification {

    /**
     * id ресурса (many to one)
     */
    private static Specification<Accounts> findAll() {
        return (root, query, criteriaBuilder) -> {
            Join<Accounts, Res> accountRes = root.join(Accounts_.RES);
            return criteriaBuilder.equal(accountRes.get(Res_.CODE_RES), 174L);
        };
    }

    private static Specification<Accounts> loginEqual(String login) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Accounts_.LOGIN_ACC), login);
    }

    public static Specification<Accounts> organizationEntityKod(String kod) {
        return (root, query, criteriaBuilder) -> {
            Join<Accounts, Employees> accountsEmployees = root.join(Accounts_.EMPLOYEES);
            Join<Employees, OrganizationEntity> employeesOrganizationEntity = accountsEmployees.join(Employees_.ORGANIZATION_ENTITY);
            return criteriaBuilder.equal(employeesOrganizationEntity.get(OrganizationEntity_.CODE), kod);
        };
    }

    public static Specification<Accounts> organizationEntityId(Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<Accounts, Employees> accountsEmployees = root.join(Accounts_.EMPLOYEES);
            Join<Employees, OrganizationEntity> employeesOrganizationEntity = accountsEmployees.join(Employees_.ORGANIZATION_ENTITY);
            return criteriaBuilder.equal(employeesOrganizationEntity.get(OrganizationEntity_.ID), id);
        };
    }

    public static Specification<Accounts> findAllOrganizationKod(String kod) {
        return findAll().and(
                organizationEntityKod(kod)
        );
    }

    public static Specification<Accounts> findByLogin(String login) {
        return findAll().and(
                loginEqual(login)
        );
    }

}
