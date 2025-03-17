package com.se2.tut5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se2.tut5.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
