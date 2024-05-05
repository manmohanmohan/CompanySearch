package com.company.search.repositories;

import com.company.search.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Optional<List<Company>> findByCompanyNameAndCompanyStatus(String name, String status);
    Optional<Company> findByCompanyNumberAndCompanyStatus(String companyNumber, String status);
}