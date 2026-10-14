package com.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object for creating and updating a Company.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    @NotBlank(message = "Company name is required")
    private String name;

    private String industry;

    private String website;

    private String location;

    private String notes;
}
