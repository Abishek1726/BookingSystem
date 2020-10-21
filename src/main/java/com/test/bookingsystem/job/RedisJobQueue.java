package com.test.bookingsystem.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.*;

public class RedisJobQueue implements JobQueue {
    @Autowired
    RedisTemplate<String, Job> redisJobTemplate;

    private static final String QUEUE_KEY = "BOOKING_SYSTEM_JOB_QUEUE";

    @Override
    public Collection<Job> getJobs() {
        Long now = System.currentTimeMillis();
        List<Object> results = redisJobTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                List results = new ArrayList<>();
                Set<Job> jobs = redisJobTemplate.opsForZSet().rangeByScore(QUEUE_KEY,-1, now, 0, 10);
                results.add( jobs );
                for(Job job : jobs) {
                    results.add(redisJobTemplate.opsForZSet().remove(QUEUE_KEY, job));
                }
                return results;
            }
        });
        return (Collection<Job>) results.get(0);
    }

    @Override
    public void perform(Job job) {
        performIn(job, 0L);
    }

    @Override
    public void performIn(Job job, Long timeInMillis) {
        System.out.println("New Job added ::: Name - " + job.getJobName());
        Long timeStamp = System.currentTimeMillis() + timeInMillis;
        redisJobTemplate.opsForZSet().add(QUEUE_KEY, job, timeStamp);
    }
}
