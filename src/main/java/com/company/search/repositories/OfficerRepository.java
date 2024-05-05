package com.company.search.repositories;

import com.company.search.entities.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

    List<Officer> findAllByCompanyCompanyNumber(String companyNumber);
}