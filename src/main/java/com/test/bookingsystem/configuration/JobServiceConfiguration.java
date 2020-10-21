package com.test.bookingsystem.configuration;

import com.test.bookingsystem.job.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

@Configuration
public class JobServiceConfiguration {
    @Bean
    public JobRunner jobRunner() {
        return new JobRunnerImpl();
    }

    @Bean
    public JobQueue jobQueue() {
        return new RedisJobQueue();
    }

    @Bean
    TaskExecutor taskExecutor () {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(50);
        t.setAllowCoreThreadTimeOut(true);
        t.setKeepAliveSeconds(120);
        return t;
    }

    @Bean
    JobWorkerFactory jobFactory() {
        return new JobWorkerFactory();
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        //load details from property file
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Job> redisJobTemplate() {
        RedisTemplate<String, Job> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }

}
