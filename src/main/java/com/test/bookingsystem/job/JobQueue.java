package com.test.bookingsystem.job;

import java.util.Collection;

public interface JobQueue {
    Collection<Job> getJobs();
    void perform(Job job);
    void performIn(Job job, Long timeInMillis);
}