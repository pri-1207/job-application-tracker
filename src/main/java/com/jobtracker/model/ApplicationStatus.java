package com.jobtracker.model;

/**
 * Enumeration representing the possible statuses of a job application.
 *
 * <p>Tracks the application lifecycle from initial submission through
 * to final outcome (offer or rejection).</p>
 */
public enum ApplicationStatus {
    /** Application has been submitted but not yet reviewed. */
    APPLIED,

    /** Application is currently under review by the company. */
    UNDER_REVIEW,

    /** Candidate has been contacted for a phone screen. */
    PHONE_SCREEN,

    /** Candidate is scheduled for or has completed an interview. */
    INTERVIEWING,

    /** Candidate has completed a technical assessment. */
    TECHNICAL_ASSESSMENT,

    /** Candidate has received a job offer. */
    OFFER_RECEIVED,

    /** Candidate has accepted the job offer. */
    OFFER_ACCEPTED,

    /** Candidate has declined the job offer. */
    OFFER_DECLINED,

    /** Application has been rejected by the company. */
    REJECTED,

    /** Application has been withdrawn by the candidate. */
    WITHDRAWN
}
