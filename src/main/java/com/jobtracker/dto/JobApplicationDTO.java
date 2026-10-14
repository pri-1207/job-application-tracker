package com.jobtracker.dto;

import com.jobtracker.model.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating and updating a Job Application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationDTO {

    @NotBlank(message = "Role title is required")
    private String roleTitle;

    private String description;

    private ApplicationStatus status;

    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;

    private String salaryRange;

    private String jobUrl;

    private String location;

    private String workType;

    private String notes;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}
