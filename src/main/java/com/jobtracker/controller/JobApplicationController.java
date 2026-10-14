package com.jobtracker.controller;

import com.jobtracker.dto.JobApplicationDTO;
import com.jobtracker.model.ApplicationStatus;
import com.jobtracker.model.JobApplication;
import com.jobtracker.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing job applications.
 *
 * <p>Provides endpoints for CRUD operations, dynamic filtering and sorting
 * by application status, company, role, and application date.</p>
 */
@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Job Applications", description = "Job application management endpoints")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    @GetMapping
    @Operation(summary = "Get all applications",
               description = "Retrieve all job applications with pagination and sorting")
    public ResponseEntity<Page<JobApplication>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(applicationService.getAllApplications(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application by ID",
               description = "Retrieve a single job application by its ID")
    public ResponseEntity<JobApplication> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter applications",
               description = "Dynamic filtering by status, company, role title, and date range with sorting")
    public ResponseEntity<Page<JobApplication>> filterApplications(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String roleTitle,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(applicationService.filterApplications(
                status, companyId, roleTitle, startDate, endDate, page, size, sortBy, sortDir));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get applications by status",
               description = "Retrieve all applications with a specific status")
    public ResponseEntity<List<JobApplication>> getByStatus(@PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.getByStatus(status));
    }

    @PostMapping
    @Operation(summary = "Create an application",
               description = "Create a new job application entry")
    public ResponseEntity<JobApplication> createApplication(
            @Valid @RequestBody JobApplicationDTO dto) {
        JobApplication created = applicationService.createApplication(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an application",
               description = "Update an existing job application by ID")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationDTO dto) {
        return ResponseEntity.ok(applicationService.updateApplication(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update application status",
               description = "Update only the status of a job application")
    public ResponseEntity<JobApplication> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        ApplicationStatus status = ApplicationStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an application",
               description = "Delete a job application by ID")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{status}")
    @Operation(summary = "Count by status",
               description = "Get the count of applications with a specific status")
    public ResponseEntity<Map<String, Long>> countByStatus(@PathVariable ApplicationStatus status) {
        long count = applicationService.countByStatus(status);
        return ResponseEntity.ok(Map.of("status", (long) status.ordinal(), "count", count));
    }
}
