package com.jobtracker.repository;

import com.jobtracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link Company} entities.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * Find companies whose name contains the given string (case-insensitive).
     */
    List<Company> findByNameContainingIgnoreCase(String name);

    /**
     * Find companies by industry (case-insensitive).
     */
    List<Company> findByIndustryIgnoreCase(String industry);

    /**
     * Check if a company with the given name already exists (case-insensitive).
     */
    boolean existsByNameIgnoreCase(String name);
}
