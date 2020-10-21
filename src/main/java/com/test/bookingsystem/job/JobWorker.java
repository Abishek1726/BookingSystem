package com.test.bookingsystem.job;

import java.util.Map;

public abstract class JobWorker<T> implements Runnable{
    private Job job;
    JobWorker(Job job) {
        this.job = job;
    }
    abstract void perform(T t);
    abstract T transformJobInfo(Map<String,String> jobInfo);

    public void run() {
        perform(transformJobInfo(job.getJobInfo()));
    }

    void execute(Map<String,String> jobInfo) {
        perform(transformJobInfo(jobInfo));
    }

}
