package com.test.bookingsystem.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class JobRunnerImpl implements JobRunner{
//    @Value("${app.jobservice.thread_pool_size}")
    Integer threadPoolSize = 5;

//    @Value("${app.jobservice.poll_time}")
    Integer maxPollWaitTime = 3;

    final Random random = new Random();

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    JobWorkerFactory jobWorkerFactory;

    @Override
    public void run(JobQueue jobQueue) {
        while (true) {
            Collection<Job> jobs = jobQueue.getJobs();
            for(Job job : jobs) {
                JobWorker jobWorker = jobWorkerFactory.createWorker(job);
                taskExecutor.execute(jobWorker);
            }
         sleep();
        }
    }

    private void sleep() {
        try {
            Integer sleepTimeInSeconds = random.nextInt(maxPollWaitTime) + 1;
            Thread.sleep(sleepTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
