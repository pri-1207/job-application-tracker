package com.jobtracker.model;

/**
 * Enumeration representing the stages of an interview process.
 */
public enum InterviewStage {
    /** Initial phone or video screening call. */
    PHONE_SCREENING,

    /** First-round technical or behavioral interview. */
    FIRST_ROUND,

    /** Second-round in-depth interview. */
    SECOND_ROUND,

    /** Technical coding or system design assessment. */
    TECHNICAL,

    /** Behavioral or cultural fit interview. */
    BEHAVIORAL,

    /** On-site or final-round interview. */
    ONSITE,

    /** Interview with the hiring manager. */
    HIRING_MANAGER,

    /** Panel interview with multiple interviewers. */
    PANEL,

    /** Final interview before offer decision. */
    FINAL_ROUND
}
