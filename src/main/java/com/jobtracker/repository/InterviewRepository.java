package com.jobtracker.repository;

import com.jobtracker.model.Interview;
import com.jobtracker.model.InterviewStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for {@link Interview} entities.
 */
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    /**
     * Find all interviews for a given job application.
     */
    List<Interview> findByJobApplicationId(Long jobApplicationId);

    /**
     * Find all interviews by stage.
     */
    List<Interview> findByStage(InterviewStage stage);

    /**
     * Find upcoming interviews scheduled after a given time.
     */
    List<Interview> findByScheduledAtAfterOrderByScheduledAtAsc(LocalDateTime dateTime);

    /**
     * Find interviews by completion status.
     */
    List<Interview> findByCompleted(Boolean completed);
}
