package com.test.bookingsystem.job;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum JobMappings {
    REJECT_BOOKED_TICKET_JOB("RejectBookedTicketJob"),
    DUMMY_JOB("Dummy");

    @Getter
    private String jobName;
    JobMappings(String jobName) {
        this.jobName = jobName;
    }

    public static Optional<JobMappings> getJobMapping(String jobName) {
        JobMappings jobMapping = null;
        for(JobMappings j : values()) {
            if(j.jobName.equals(jobName)) {
                jobMapping = j;
            }
        }
        return Optional.ofNullable(jobMapping);
    }

}