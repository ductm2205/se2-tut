package com.se2.tut5.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.se2.tut5.model.Company;
import com.se2.tut5.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

@Repository
public class EmployeeDao {

    private EntityManager entityManager;
    private CompanyRepository companyRepository;

    public EmployeeDao(EntityManager entityManager, CompanyRepository companyRepository) {
        this.entityManager = entityManager;
        this.companyRepository = companyRepository;
    }

    public Page<Employee> filterAndSortEmployees(Long id, int gender, int sortMode, Pageable pageable) {
        // Use HibernateCriteriaBuilder instead of CriteriaBuilder
        HibernateCriteriaBuilder criteriaBuilder = entityManager.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);

        Path<Object> sortColumn = employee.get("id");

        if (sortMode == 2 || sortMode == 3) {
            sortColumn = employee.get("name");
        }

        Order sortOrder = criteriaBuilder.desc(sortColumn);
        if (sortMode == 1 || sortMode == 2) {
            sortOrder = criteriaBuilder.asc(sortColumn);
        }

        List<Predicate> predicates = new ArrayList<>();

        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {
            predicates.add(criteriaBuilder.equal(employee.get("company"), company.get()));
        }

        if (gender == 1) {
            predicates.add(criteriaBuilder.equal(employee.get("male"), false));
        } else if (gender == 2) {
            predicates.add(criteriaBuilder.equal(employee.get("male"), true));
        }
        
        // add a list of predicates as query criteria
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        
        // sort
        query.orderBy(sortOrder);

        // Apply pagination
        TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Return a Page object with pagination information
        return PageableExecutionUtils.getPage(
            typedQuery.getResultList(),
            pageable,
            () -> entityManager.createQuery(query.createCountQuery()).getSingleResult()
        );
    }
}
