package com.jobtracker.dto;

import com.jobtracker.model.InterviewStage;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating and updating an Interview.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewDTO {

    @NotNull(message = "Interview stage is required")
    private InterviewStage stage;

    private LocalDateTime scheduledAt;

    private String interviewer;

    private String location;

    private String notes;

    private String feedback;

    private Boolean completed;

    @NotNull(message = "Job Application ID is required")
    private Long jobApplicationId;
}
