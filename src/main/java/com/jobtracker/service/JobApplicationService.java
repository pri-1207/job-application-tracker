package com.jobtracker.service;

import com.jobtracker.dto.JobApplicationDTO;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.model.ApplicationStatus;
import com.jobtracker.model.Company;
import com.jobtracker.model.JobApplication;
import com.jobtracker.repository.CompanyRepository;
import com.jobtracker.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for managing {@link JobApplication} entities.
 *
 * <p>Provides full CRUD operations plus dynamic filtering and sorting
 * by application status, company, role, and date range.</p>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    /**
     * Retrieve all applications with pagination and sorting.
     */
    @Transactional(readOnly = true)
    public Page<JobApplication> getAllApplications(int page, int size,
                                                    String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(
                sortDir != null ? sortDir : "desc"),
                sortBy != null ? sortBy : "applicationDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return applicationRepository.findAll(pageable);
    }

    /**
     * Retrieve a single application by ID.
     */
    @Transactional(readOnly = true)
    public JobApplication getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Application", id));
    }

    /**
     * Dynamic filtering and sorting of applications.
     *
     * @param status     filter by application status (nullable)
     * @param companyId  filter by company ID (nullable)
     * @param roleTitle  filter by role title substring (nullable)
     * @param startDate  filter by application date start (nullable)
     * @param endDate    filter by application date end (nullable)
     * @param page       page number (0-indexed)
     * @param size       page size
     * @param sortBy     field to sort by
     * @param sortDir    sort direction (asc/desc)
     * @return paginated and filtered results
     */
    @Transactional(readOnly = true)
    public Page<JobApplication> filterApplications(ApplicationStatus status, Long companyId,
                                                    String roleTitle, LocalDate startDate,
                                                    LocalDate endDate, int page, int size,
                                                    String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(
                sortDir != null ? sortDir : "desc"),
                sortBy != null ? sortBy : "applicationDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return applicationRepository.findWithFilters(status, companyId, roleTitle,
                startDate, endDate, pageable);
    }

    /**
     * Get applications by status.
     */
    @Transactional(readOnly = true)
    public List<JobApplication> getByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    /**
     * Create a new job application.
     */
    public JobApplication createApplication(JobApplicationDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", dto.getCompanyId()));

        JobApplication application = JobApplication.builder()
                .roleTitle(dto.getRoleTitle())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : ApplicationStatus.APPLIED)
                .applicationDate(dto.getApplicationDate())
                .salaryRange(dto.getSalaryRange())
                .jobUrl(dto.getJobUrl())
                .location(dto.getLocation())
                .workType(dto.getWorkType())
                .notes(dto.getNotes())
                .company(company)
                .build();
        return applicationRepository.save(application);
    }

    /**
     * Update an existing job application.
     */
    public JobApplication updateApplication(Long id, JobApplicationDTO dto) {
        JobApplication application = getApplicationById(id);

        if (dto.getCompanyId() != null &&
                !dto.getCompanyId().equals(application.getCompany().getId())) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", dto.getCompanyId()));
            application.setCompany(company);
        }

        application.setRoleTitle(dto.getRoleTitle());
        application.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            application.setStatus(dto.getStatus());
        }
        application.setApplicationDate(dto.getApplicationDate());
        application.setSalaryRange(dto.getSalaryRange());
        application.setJobUrl(dto.getJobUrl());
        application.setLocation(dto.getLocation());
        application.setWorkType(dto.getWorkType());
        application.setNotes(dto.getNotes());

        return applicationRepository.save(application);
    }

    /**
     * Update only the status of an application.
     */
    public JobApplication updateStatus(Long id, ApplicationStatus status) {
        JobApplication application = getApplicationById(id);
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    /**
     * Delete an application by ID.
     */
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job Application", id);
        }
        applicationRepository.deleteById(id);
    }

    /**
     * Get application count by status.
     */
    @Transactional(readOnly = true)
    public long countByStatus(ApplicationStatus status) {
        return applicationRepository.countByStatus(status);
    }
}
