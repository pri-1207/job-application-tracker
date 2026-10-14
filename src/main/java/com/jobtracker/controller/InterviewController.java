package com.jobtracker.controller;

import com.jobtracker.dto.InterviewDTO;
import com.jobtracker.model.Interview;
import com.jobtracker.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing interviews.
 *
 * <p>Provides endpoints for CRUD operations and querying interviews
 * by application or upcoming schedule.</p>
 */
@RestController
@RequestMapping("/api/v1/interviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Interviews", description = "Interview management endpoints")
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping
    @Operation(summary = "Get all interviews", description = "Retrieve all interviews")
    public ResponseEntity<List<Interview>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.getAllInterviews());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interview by ID", description = "Retrieve a single interview by its ID")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getInterviewById(id));
    }

    @GetMapping("/application/{applicationId}")
    @Operation(summary = "Get interviews by application",
               description = "Retrieve all interviews for a specific job application")
    public ResponseEntity<List<Interview>> getByApplicationId(
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(interviewService.getInterviewsByApplicationId(applicationId));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming interviews",
               description = "Retrieve all interviews scheduled in the future, ordered by date")
    public ResponseEntity<List<Interview>> getUpcomingInterviews() {
        return ResponseEntity.ok(interviewService.getUpcomingInterviews());
    }

    @PostMapping
    @Operation(summary = "Create an interview", description = "Schedule a new interview")
    public ResponseEntity<Interview> createInterview(@Valid @RequestBody InterviewDTO dto) {
        Interview created = interviewService.createInterview(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an interview", description = "Update an existing interview by ID")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long id,
                                                      @Valid @RequestBody InterviewDTO dto) {
        return ResponseEntity.ok(interviewService.updateInterview(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an interview", description = "Delete an interview by ID")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.noContent().build();
    }
}
