package com.jobtracker.repository;

import com.jobtracker.model.ApplicationStatus;
import com.jobtracker.model.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for {@link JobApplication} entities.
 *
 * <p>Extends {@link JpaSpecificationExecutor} to support dynamic filtering
 * and sorting via Specification queries.</p>
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>,
        JpaSpecificationExecutor<JobApplication> {

    /**
     * Find all applications by status.
     */
    List<JobApplication> findByStatus(ApplicationStatus status);

    /**
     * Find all applications for a given company.
     */
    List<JobApplication> findByCompanyId(Long companyId);

    /**
     * Find applications by status with pagination.
     */
    Page<JobApplication> findByStatus(ApplicationStatus status, Pageable pageable);

    /**
     * Find applications within a date range.
     */
    List<JobApplication> findByApplicationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Count applications by status.
     */
    long countByStatus(ApplicationStatus status);

    /**
     * Dynamic search: filter by status, company name, role title, and date range.
     */
    @Query("SELECT j FROM JobApplication j " +
           "WHERE (:status IS NULL OR j.status = :status) " +
           "AND (:companyId IS NULL OR j.company.id = :companyId) " +
           "AND (:roleTitle IS NULL OR LOWER(j.roleTitle) LIKE LOWER(CONCAT('%', :roleTitle, '%'))) " +
           "AND (:startDate IS NULL OR j.applicationDate >= :startDate) " +
           "AND (:endDate IS NULL OR j.applicationDate <= :endDate)")
    Page<JobApplication> findWithFilters(
            @Param("status") ApplicationStatus status,
            @Param("companyId") Long companyId,
            @Param("roleTitle") String roleTitle,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
