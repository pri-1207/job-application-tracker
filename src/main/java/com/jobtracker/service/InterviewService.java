package com.jobtracker.service;

import com.jobtracker.dto.InterviewDTO;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.model.Interview;
import com.jobtracker.model.JobApplication;
import com.jobtracker.repository.InterviewRepository;
import com.jobtracker.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for managing {@link Interview} entities.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final JobApplicationRepository applicationRepository;

    /**
     * Retrieve all interviews.
     */
    @Transactional(readOnly = true)
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    /**
     * Retrieve a single interview by ID.
     */
    @Transactional(readOnly = true)
    public Interview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", id));
    }

    /**
     * Retrieve all interviews for a given job application.
     */
    @Transactional(readOnly = true)
    public List<Interview> getInterviewsByApplicationId(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("Job Application", applicationId);
        }
        return interviewRepository.findByJobApplicationId(applicationId);
    }

    /**
     * Retrieve upcoming interviews.
     */
    @Transactional(readOnly = true)
    public List<Interview> getUpcomingInterviews() {
        return interviewRepository.findByScheduledAtAfterOrderByScheduledAtAsc(LocalDateTime.now());
    }

    /**
     * Create a new interview.
     */
    public Interview createInterview(InterviewDTO dto) {
        JobApplication application = applicationRepository.findById(dto.getJobApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job Application", dto.getJobApplicationId()));

        Interview interview = Interview.builder()
                .stage(dto.getStage())
                .scheduledAt(dto.getScheduledAt())
                .interviewer(dto.getInterviewer())
                .location(dto.getLocation())
                .notes(dto.getNotes())
                .feedback(dto.getFeedback())
                .completed(dto.getCompleted() != null ? dto.getCompleted() : false)
                .jobApplication(application)
                .build();
        return interviewRepository.save(interview);
    }

    /**
     * Update an existing interview.
     */
    public Interview updateInterview(Long id, InterviewDTO dto) {
        Interview interview = getInterviewById(id);

        if (dto.getJobApplicationId() != null &&
                !dto.getJobApplicationId().equals(interview.getJobApplication().getId())) {
            JobApplication application = applicationRepository.findById(dto.getJobApplicationId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Job Application", dto.getJobApplicationId()));
            interview.setJobApplication(application);
        }

        interview.setStage(dto.getStage());
        interview.setScheduledAt(dto.getScheduledAt());
        interview.setInterviewer(dto.getInterviewer());
        interview.setLocation(dto.getLocation());
        interview.setNotes(dto.getNotes());
        interview.setFeedback(dto.getFeedback());
        if (dto.getCompleted() != null) {
            interview.setCompleted(dto.getCompleted());
        }

        return interviewRepository.save(interview);
    }

    /**
     * Delete an interview by ID.
     */
    public void deleteInterview(Long id) {
        if (!interviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Interview", id);
        }
        interviewRepository.deleteById(id);
    }
}
