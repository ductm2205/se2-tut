package se2.tut02.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se2.tut02.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
