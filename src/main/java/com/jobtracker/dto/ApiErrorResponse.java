package com.jobtracker.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard API error response body.
 *
 * <p>Used by the centralized exception handler to return consistent
 * error responses across all endpoints.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private int status;

    private String error;

    private String message;

    private String path;

    private LocalDateTime timestamp;

    /** Field-level validation errors (field name → error message). */
    private Map<String, String> validationErrors;
}
