package com.jobtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Job Application Tracking System.
 *
 * <p>This Spring Boot application provides a RESTful API for managing
 * job applications, companies, interview stages, and application statuses.</p>
 *
 * @author Job Tracker Team
 * @version 1.0.0
 */
@SpringBootApplication
public class JobApplicationTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplicationTrackerApplication.class, args);
    }
}
