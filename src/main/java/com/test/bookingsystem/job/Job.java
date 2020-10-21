package com.test.bookingsystem.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
public class Job implements Serializable {
    @Getter
    private final String jobName;

    @Getter
    private final Map<String,String> jobInfo;
}
